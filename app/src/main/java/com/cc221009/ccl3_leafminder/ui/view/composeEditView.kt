package com.cc221009.ccl3_leafminder.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.PlantDetails
import com.cc221009.ccl3_leafminder.data.makePlantRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view_model.CameraViewModel
import com.cc221009.ccl3_leafminder.ui.view_model.EditPlantViewModel

@Composable
fun EditView(
    plantId: Int,
    vm: EditPlantViewModel = viewModel(
        factory = EditPlantViewModel.provideFactory(
            makePlantRepository(LocalContext.current)
        )
    ),
    navController: NavController,
    cameraViewModel: CameraViewModel
) {

    val state by vm.uiState.collectAsState()
    val capturedImageUriState = cameraViewModel.capturedImageUri
    val capturedImageUri = capturedImageUriState.value

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
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                onDismissRequest = { /* Dismiss logic here */ },
                title = { H4TextLeft("Are you sure you want to delete this plant?") },
                confirmButton = {
                    PrimaryButton2("Delete",
                        onClickLogic = {
                            val plant = Plant(
                                name = state.name.text,
                                date = state.date,
                                size = state.size,
                                location = state.location,
                                wellbeing = state.wellbeing,
                                wateringDate = state.wateringDate,
                                wateringFrequency = state.wateringFrequency,
                                imagePath = capturedImageUri.toString(),
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
                    SecondaryButton("Cancel",
                        onClickLogic = {
                            state.clickDismissDialog()
                        })
                },
            )

        }


        // Header
        Header("Edit this plant", R.drawable.icon_delete,
            leftIconLogic = {
                navController.popBackStack()
            }, rightIconLogic = {
                state.clickShowDialog()
            })


        // Profile Image
        PlantImage(
            state.imagePath,
            capturedImgUri = capturedImageUri,
            onClickLogic = {
                navController.navigate(Screen.CameraView.route)
            })

        Log.e("name text outside", state.plant.toString())

        Log.e("name text outside", state.name.toString())

        // Textfield Name
        DefaultTextField(
            "Name",
            null,
            text = state.name,
            onValueChange = state.onNameChange,
        )

        AddPlantInfoContainer(
            state.date,
            setDate = state.onDateChange,
            state.size,
            setSize = state.onSizeChange,
            state.location,
            setLocation = state.onLocationChange,
            state.wellbeing,
            setWellbeing = state.onWellbeingChange,

            )


        AddPlantSpeciesContainer(
            speciesNames = emptyList(), onDropdownTapped = {}, plantDetails = PlantDetails(
                listOf("sunny"),
                "frequent",
                false,

                )
        )

        AddPlantWateringContainer(
            state.wateringDate,
            state.setWateringFrequency,
            state.setWateringDate,
            state.waterInterval
        )

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
                    imagePath = capturedImageUri?.toString() ?: "",
                    id = state.plant?.id ?: 0
                )
                state.onSaveEditedPlant(editedPlant)
                println("Button was clicked")

                navController.navigate(Screen.PlantListView.route) {
                    popUpTo(Screen.PlantListView.route) {
                        inclusive = true
                    }
                }
            })
    }

}