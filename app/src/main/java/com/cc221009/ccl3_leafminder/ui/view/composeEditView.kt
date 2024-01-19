package com.cc221009.ccl3_leafminder.ui.view

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
import com.cc221009.ccl3_leafminder.data.model.Plants
import com.cc221009.ccl3_leafminder.ui.view_model.EditPlantViewModel

data class EditUIState(
    val name: String,
    val date: String,
    val size: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,
    val onNameChange: (String) -> Unit,
    val onDateChane: (String) -> Unit,
    val onSizeChange: (String) -> Unit,
    val onWellbeingChange: (String) -> Unit,
    val onWateringDateChange: (String) -> Unit,
    val onWateringFrequencyChange: (String) -> Unit,
    val onImagePathChange: (String) -> Unit,

    val onSaveEditedPlant: (Plants) -> Unit,

    val openDialog: Boolean,

    val clickDismissDialog: () -> Unit,

    val clickShowDialog: () -> Unit,

    val clickingToDeletePlant: (Plants) -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
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
                            val plant = Plants(
                                name = state.name,
                                date = state.date,
                                size = state.size,
                                wellbeing = state.wellbeing,
                                wateringDate = state.wateringDate,
                                wateringFrequency = state.wateringFrequency,
                                imagePath = state.imagePath
                            )
                            state.clickingToDeletePlant(plant)
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
            text = TextFieldValue(""),
            onValueChange = {}
        )

        AddPlantInfoContainer()

        AddPlantSpeciesContainer(speciesNames = emptyList(), onDropdownTapped = {})

        AddPlantWateringContainer()

        PrimaryButton("Save Changes",
            onClickLogic = {
                val plant = Plants(
                    name = state.name,
                    date = state.date,
                    size = state.size,
                    wellbeing = state.wellbeing,
                    wateringDate = state.wateringDate,
                    wateringFrequency = state.wateringFrequency,
                    imagePath = state.imagePath
                )
                state.onSaveEditedPlant(plant)
                println("Button was clicked")
            })
    }

}