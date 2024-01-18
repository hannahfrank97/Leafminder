package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.getDatabase
import com.cc221009.ccl3_leafminder.data.model.Plants
import com.cc221009.ccl3_leafminder.ui.view_model.AddPlantViewModel

data class AddUIState(
    val name: TextFieldValue,
    val setName: (TextFieldValue) -> Unit,

    val speciesNames: List<String>,
    val onSpeciesListTapped: () -> Unit,

    val tappingtoSavePlant: (Plants) -> Unit,


    val date: String,
    val size: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String

)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
    vm: AddPlantViewModel = viewModel(
        factory = AddPlantViewModel.provideFactory(
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

        // Header
        Header("Add a new plant", R.drawable.icon_minus)

        // Profile Image
        PlantImage(R.drawable.placeholder, onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })

        // Textfield Name
        DefaultTextField(
            "Name", "Name", text = state.name, onValueChange = state.setName
        )

        AddPlantInfoContainer()

        AddPlantSpeciesContainer(state.speciesNames, onDropdownTapped = state.onSpeciesListTapped)

        AddPlantWateringContainer()

        PrimaryButton("Add Plant",
            onClickLogic = {
                    val plant = Plants(
                        name = state.name.text,
                        date = state.date,
                        size = state.size,
                        wellbeing = state.wellbeing,
                        wateringDate = state.wateringDate,
                        wateringFrequency = state.wateringFrequency,
                        imagePath = state.imagePath

                )
                state.tappingtoSavePlant(plant)
                println("Button was clicked")
            })
    }

}

// INFO COMPONENTS

@Composable
fun AddPlantInfoContainer(
) {
    Column(
        modifier = Modifier
            .background(colorScheme.secondary)
            .padding(20.dp)
    ) {
        // TODO: Connect to Viewmodel
        DefaultTextField("Since when do you have your plant?",
            "Select date",
            text = TextFieldValue(""),
            onValueChange = { })

        AddParameterContainer("size") {
            IconButtonsItem(
                "small", R.drawable.placeholder, "small", modifier = Modifier.weight(1f)
            )
            IconButtonsItem(
                "medium", R.drawable.placeholder, "medium", modifier = Modifier.weight(1f)
            )
            IconButtonsItem(
                "large", R.drawable.placeholder, "large", modifier = Modifier.weight(1f)
            )
        }

        AddParameterContainer("location") {
            IconButtonsItem(
                "light", R.drawable.placeholder, "light", modifier = Modifier.weight(1f)
            )
            IconButtonsItem(
                "half-light", R.drawable.placeholder, "half-light", modifier = Modifier.weight(1f)
            )
            IconButtonsItem(
                "half-shadow", R.drawable.placeholder, "half-shadow", modifier = Modifier.weight(1f)
            )
            IconButtonsItem(
                "shadow", R.drawable.placeholder, "shadow", modifier = Modifier.weight(1f)
            )
        }

        AddParameterContainer("wellbeing") {
            IconButtonsItem(
                "great", R.drawable.placeholder, "great", modifier = Modifier.weight(1f)
            )
            IconButtonsItem("okay", R.drawable.placeholder, "okay", modifier = Modifier.weight(1f))
            IconButtonsItem(
                "miserable", R.drawable.placeholder, "miserable", modifier = Modifier.weight(1f)
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))


}

@Composable
fun AddParameterContainer(
    headline: String, content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = headline)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
    }
}

@Composable
fun IconButtonsItem(
    headline: String, imgPath: Int, imgDescription: String, modifier: Modifier = Modifier
) {

    Button(modifier = Modifier
        .fillMaxWidth(0.3f)
        .height(60.dp)
        .then(modifier), onClick = {
        //Logic for adding Image
    }) {
        Column {
            Image(painter = painterResource(id = imgPath), imgDescription)
            Text(text = headline)
        }
    }
}

// API COMPONENTS

@Composable
fun AddPlantSpeciesContainer(
    speciesNames: List<String>,
    onDropdownTapped: () -> Unit,
) {

    Column(
        modifier = Modifier
            .background(colorScheme.tertiary)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        //HEADLINE
        Text(text = "Species")

        // TODO: Connect to Viewmodel

        var expanded by remember { mutableStateOf(false) }
        var selectedSpecies by remember { mutableStateOf("Selected Species") }

        Box(Modifier.fillMaxWidth()) {
            Text(selectedSpecies, modifier = Modifier.padding(16.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                speciesNames.forEach { speciesName ->
                    DropdownMenuItem(onClick = {
                        selectedSpecies = speciesName
                        expanded = false

                    }) {
                        Text(speciesName)
                    }
                }
            }

            Spacer(modifier = Modifier.matchParentSize())
            IconButton(onClick = {
                expanded = true
                onDropdownTapped()

            }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Arrow")
            }
        }

    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        APIIconItem(
            "Location",
            "api value",
            R.drawable.placeholder,
            "location icon",
            modifier = Modifier.weight(1f)
        )
        APIIconItem(
            "Watering",
            "api value",
            R.drawable.placeholder,
            "watering icon",
            modifier = Modifier.weight(1f)
        )
        APIIconItem(
            "Poisinousness",
            "api value",
            R.drawable.placeholder,
            "poisonousness icon",
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun APIIconItem(
    headline: String,
    apiValue: String,
    imgPath: Int,
    imgDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(colorScheme.surface)
            .padding(20.dp)
            .fillMaxWidth(0.3f)
            .height(100.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imgPath),
            imgDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(text = headline)
        Text(text = apiValue)
    }
}

// WATERING COMPONENTS

@Composable
fun AddPlantWateringContainer() {

    Column(
        modifier = Modifier
            .background(colorScheme.secondary)
            .padding(20.dp)
    ) {
        //HEADLINE
        Text(text = "Watering")

        // TODO: Connect to Viewmodel
        DefaultTextField(
            "Select the date of last watering",
            "Select date",
            text = TextFieldValue(""),
            onValueChange = { })

        WateringFrequencySelector("How frequent do you want to water your plant?")
    }
    Spacer(modifier = Modifier.height(20.dp))

}

@Composable
fun WateringFrequencySelector(
    headline: String,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = headline)
        Row() {
            PlusMinusButton(R.drawable.icon_minus, onClickLogic = {
                // Define what should happen when the button is clicked
                println("Minus one")
            })

            Column {
                Text(text = "20") //Increase variable
                Text(text = "days") //Increase variable
            }

            PlusMinusButton(R.drawable.icon_plus, onClickLogic = {
                // Define what should happen when the button is clicked
                println("Plus one")
            })
        }
    }

}

@Composable
fun PlusMinusButton(
    imgPath: Int, onClickLogic: () -> Unit
) {
    Button(onClick = { onClickLogic() }) {
        Icon(
            painter = painterResource(id = imgPath),
            contentDescription = "Back",
        )
    }
}

@Composable
fun PlantImage(
    imgPath: Int, onClickLogic: () -> Unit
) {
    Button(modifier = Modifier
        .padding(bottom = 20.dp)
        .border(2.dp, colorScheme.outline, RoundedCornerShape(10.dp))
        .height(100.dp)
        .width(100.dp),
        onClick = { onClickLogic() }) {
        Image(
            painter = painterResource(id = imgPath),
            "Profile Picture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}





