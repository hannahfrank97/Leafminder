package com.cc221009.ccl3_leafminder.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.getDatabase
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view_model.EditPlantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
    plantId: Int,
    vm: EditPlantViewModel = viewModel(
        factory = EditPlantViewModel.provideFactory(
            PlantsRepository(
                getDatabase(LocalContext.current).dao
            )
        )
    ),

    navController: NavController
) {

    val state by vm.uiState.collectAsState()

    if (state.plant == null) {

        LaunchedEffect(key1 = plantId) {
            state.loadPlant(plantId)
            Log.d("composeEditView", "Loaded plant id: $plantId")
        }
        Text(text = "Loading...")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (state.openDialog) {
            AlertDialog(
                onDismissRequest = { /* Dismiss logic here */ },
                title = { Text("Are you sure you want to delete this plant?") },
                confirmButton = {
                    PrimaryButton("Delete",
                        onClickLogic = {
                            val plant = Plant(
                                name = state.name.text,
                                date = state.date,
                                size = state.size,
                                location = state.location,
                                wellbeing = state.wellbeing,
                                wateringDate = state.wateringDate,
                                wateringFrequency = state.wateringFrequency,
                                imagePath = state.imagePath,
                                id = state.plant?.id ?: 0
                            )
                            state.clickingToDeletePlant(plant)

                            navController.navigate(Screen.PlantListView.route) {
                                popUpTo(Screen.PlantListView.route) {
                                    inclusive = true
                                }
                            }

                        })


                },

                dismissButton = {
                    PrimaryButton("Cancel",
                        onClickLogic = {
                            state.clickDismissDialog()
                        })
                }
            )

        }


        // Header
        Header("Edit this plant", R.drawable.icon_delete,
            leftIconLogic = {
                navController.navigate(Screen.DetailView.route)
            }, rightIconLogic = {
                state.clickShowDialog()
            })

        // Profile Image
        PlantImage(R.drawable.placeholder, onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })

        // Textfield Name
        DefaultTextField("Name", "Name",
            text = state.name,
            onValueChange = state.onNameChange,
        )

        AddPlantInfoContainer(
            state.date,
            setDate = state.onDateChange,
            setSize = state.onSizeChange,
            setLocation = state.onLocationChange,
            setWellbeing = state.onWellbeingChange,

        )


        AddPlantSpeciesContainer(speciesNames = emptyList(), onDropdownTapped = {})

        AddPlantWateringContainer(state.wateringDate,state.setWateringFrequency, state.setWateringDate, state.waterInterval)

        PrimaryButton("Save Changes",
            onClickLogic = {
                val editedPlant = Plant(
                    name = state.name.text,
                    date = state.date,
                    size = state.size,
                    location = state.location,
                    wellbeing = state.wellbeing,
                    wateringDate = state.wateringDate,
                    wateringFrequency = state.wateringFrequency,
                    imagePath = state.imagePath,
                    id = state.plant?.id ?: 0
                )
                state.onSaveEditedPlant(editedPlant)
                println("Button was clicked")
            })
    }

}