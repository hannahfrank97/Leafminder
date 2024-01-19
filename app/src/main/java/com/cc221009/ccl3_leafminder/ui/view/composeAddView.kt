package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        Header("Add a new plant", null,
            leftIconLogic = {
                navController.navigate(Screen.HomeView.route)
            }, rightIconLogic = {})

        // Profile Image
        PlantImage(R.drawable.placeholder, onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })

        // Textfield Name
        Box(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            DefaultTextField(
                "Name", "Name", text = state.name, onValueChange = state.setName
            )
        }


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
fun AddPlantInfoContainer() {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(colorScheme.tertiaryContainer)
            .padding(20.dp)
    ) {
        // TODO: Connect to Viewmodel

        CalendarTextField("Since when do you have your plant?",
            "Select date",
            selectedDate = toString(),
            onValueChange = { })

        AddParameterContainer("size") {
            IconButtonsItem(
                "small",
                R.drawable.plant_base_small,
                "small",
                1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "medium",
                R.drawable.plant_base_medium,
                "medium",
                2,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "large",
                R.drawable.plant_base_large,
                "large",
                3,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AddParameterContainer("location") {
            IconButtonsItem(
                "light",
                R.drawable.location_light,
                "light",
                1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "half-light",
                R.drawable.location_half_light,
                "half-light",
                2,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "half-shadow",
                R.drawable.location_half_shadow,
                "half-shadow",
                3,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "shadow",
                R.drawable.location_shadow,
                "shadow",
                4,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AddParameterContainer("wellbeing") {
            IconButtonsItem(
                "great",
                R.drawable.wellbeing_good,
                "great",
                1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "okay",
                R.drawable.wellbeing_okay,
                "okay",
                2,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "miserable",
                R.drawable.wellbeing_bad,
                "miserable",
                3,
                modifier = Modifier.weight(1f)
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

        H3Text(text = headline)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
    }
}

@Composable
fun IconButtonsItem(
    headline: String,
    imgPath: Int,
    imgDescription: String,
    passValue: Int,
    modifier: Modifier = Modifier
) {
    var isClicked: Boolean = false

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                isClicked = true

                if (isClicked) {
                    // TODO ENTER BUTTON LOGIC HERE
                }


            }
            .fillMaxHeight()
            .background(colorScheme.onError)
            .padding(top = 20.dp, bottom = 20.dp)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = imgPath), imgDescription)
            Spacer(modifier = Modifier.height(10.dp))
            CopyText(text = headline)
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
            .clip(RoundedCornerShape(15.dp))
            .background(colorScheme.surface)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        //HEADLINE
        H3Text(text = "Species")

        // TODO: Connect to Viewmodel

        var expanded by remember { mutableStateOf(false) }
        var selectedSpecies by remember { mutableStateOf("Selected Species") }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                ) // Apply border
                .clip(RoundedCornerShape(12.dp)), // Then clip to the same shape
            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier.padding(start = 40.dp)
            ) {
                CopyText(selectedSpecies)
            }
            DropdownMenu(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(colorScheme.background),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                speciesNames.forEach { speciesName ->
                    DropdownMenuItem(onClick = {
                        selectedSpecies = speciesName
                        expanded = false
                    }) {
                        CopyText(speciesName)
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
    Spacer(modifier = Modifier.height(20.dp))

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
        H4Text(text = headline)
        CopyText(text = apiValue)
    }
}

// WATERING COMPONENTS

@Composable
fun AddPlantWateringContainer() {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(colorScheme.secondaryContainer)
            .padding(20.dp)

    ) {
        //HEADLINE
        H3Text(text = "Watering")

        // TODO: Connect to Viewmodel
        CalendarTextField(
            "Select the date of last watering",
            "Select date",
            selectedDate = "",
            onValueChange = { })

        WateringFrequencySelector("How frequent do you want to water your plant?")
    }
    Spacer(modifier = Modifier.height(20.dp))

}

@Composable
fun WateringFrequencySelector(
    headline: String,
) {
    var waterInterval by remember { mutableStateOf(20) } // Initial value


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CopyText(text = headline)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlusMinusButton(R.drawable.icon_minus, onClickLogic = {
                waterInterval =
                    (waterInterval - 1).coerceAtLeast(0) // Decrease and ensure not below 0
            })

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                H1Text(text = waterInterval.toString()) //Increase variable
                CopyBoldText(text = "days", colorScheme.primary) //Increase variable
            }

            PlusMinusButton(R.drawable.icon_plus, onClickLogic = {
                waterInterval++ // Increase the frequency
            })
        }
    }

}

@Composable
fun PlusMinusButton(
    imgPath: Int, onClickLogic: () -> Unit
) {

    IconButton(
        onClick = { onClickLogic() },
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .size(45.dp)
            .background(colorScheme.background),
    ) {
        Icon(
            painter = painterResource(id = imgPath),
            contentDescription = "Delete"
        )
    }
}

@Composable
fun PlantImage(
    imgPath: Int, onClickLogic: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .size(120.dp) // Set the size including the border
            .background(color = colorScheme.primary, shape = CircleShape)
            .clickable { onClickLogic() },
    ) {
        if (imgPath == null) {
            Image(
                painter = painterResource(id = R.drawable.icon_add),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(70.dp), // Clip the image to a circle shape
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = imgPath),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(115.dp), // Clip the image to a circle shape
                contentScale = ContentScale.Crop
            )
        }

    }

    Spacer(modifier = Modifier.height(20.dp))
}





