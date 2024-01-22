package com.cc221009.ccl3_leafminder.ui.view

import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.getDatabase
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view_model.AddPlantViewModel
import com.cc221009.ccl3_leafminder.ui.view_model.CameraViewModel

@Composable
fun AddView(
    vm: AddPlantViewModel = viewModel(
        factory = AddPlantViewModel.provideFactory(
            PlantsRepository(
                getDatabase(LocalContext.current).dao
            )
        )
    ),
    navController: NavController,
    cameraViewModel: CameraViewModel
) {

    val state by vm.uiState.collectAsState()
    val capturedImageUriState = cameraViewModel.capturedImageUri
    val capturedImageUri = capturedImageUriState.value



    Log.e("addview", capturedImageUri.toString())
    Log.e("addview", capturedImageUriState.toString())


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
        PlantImage(
            "",
            capturedImgUri = capturedImageUri,
            onClickLogic = {
                cameraViewModel.enableCameraPreview(true)
                navController.navigate(Screen.CameraView.route)
        })

        // Textfield Name
        Box(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            DefaultTextField(
                "Name",
                "Name",
                text = state.name,
                onValueChange = state.setName
            )
        }

        AddPlantInfoContainer(
            state.date,
            state.setDate,
            state.size,
            state.setSize,
            state.location,
            state.setLocation,
            state.wellbeing,
            state.setWellbeing)

        AddPlantSpeciesContainer(state.speciesNames, onDropdownTapped = state.onSpeciesListTapped)

        AddPlantWateringContainer(state.wateringDate,state.setWateringFrequency, state.setWateringDate, state.waterInterval)

        PrimaryButton("Add Plant",
            onClickLogic = {
                val plant = Plant(
                    name = state.name.text,
                    date = state.date,
                    size = state.size,
                    location = state.location,
                    wellbeing = state.wellbeing,
                    wateringDate = state.wateringDate,
                    wateringFrequency = state.waterInterval.toString(),
                    imagePath = capturedImageUri?.toString() ?: ""
                )
                state.tappingtoSavePlant(plant)
                println("Button was clicked")

                navController.navigate(Screen.PlantListView.route) {
                    popUpTo(Screen.PlantListView.route) {
                        inclusive = true
                    }
                }
            })
    }

}

// INFO COMPONENTS

@Composable
fun AddPlantInfoContainer(
    dateState: String,
    setDate: (String) -> Unit,
    sizeState: String,
    setSize: (String) -> Unit,
    locationState: String,
    setLocation: (String) -> Unit,
    wellbeingState: String,
    setWellbeing: (String) -> Unit,
) {

    Column(

        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(colorScheme.tertiaryContainer)
            .padding(20.dp)
    ) {

        CalendarTextField(
            "Since when do you have your plant?",
            "Select date",
            selectedDate = dateState,
            onDateChange = setDate
        )


        AddParameterContainer("size") { selectedItem, onSelectItem ->
            IconButtonsItem(
                "small",
                R.drawable.plant_base_small,
                "small",
                1,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setSize("small")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "medium",
                R.drawable.plant_base_medium,
                "medium",
                2,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setSize("medium")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "large",
                R.drawable.plant_base_large,
                "large",
                3,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setSize("large")
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AddParameterContainer("location") { selectedItem, onSelectItem ->

            IconButtonsItem(
                "light",
                R.drawable.location_light,
                "light",
                1,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setLocation("light")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "half-light",
                R.drawable.location_half_light,
                "half-light",
                2,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setLocation("half-light")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "half-shadow",
                R.drawable.location_half_shadow,
                "half-shadow",
                3,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setLocation("half-shadow")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "shadow",
                R.drawable.location_shadow,
                "shadow",
                4,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setLocation("shadow")
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AddParameterContainer("wellbeing") { selectedItem, onSelectItem ->

            IconButtonsItem(
                "great",
                R.drawable.wellbeing_good,
                "great",
                1,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setWellbeing("great")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "okay",
                R.drawable.wellbeing_okay,
                "okay",
                2,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setWellbeing("okay")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButtonsItem(
                "bad",
                R.drawable.wellbeing_bad,
                "bad",
                3,
                selectedItem,
                onSelectItem,
                modifier = Modifier.weight(1f),
                "",
                onClick = {
                    setWellbeing("bad")
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddParameterContainer(
    headline: String,
    content: @Composable (Int, (Int) -> Unit) -> Unit
    //Hannah: content: @Composable () -> Unit
) {
    var selectedItem by remember { mutableStateOf(-1) }

    Column(modifier = Modifier.fillMaxWidth()) {
        H3Text(text = headline)
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            content(selectedItem) { itemId ->
                selectedItem = itemId  // Update the selected item
            }
        }
    }
}




@Composable
fun IconButtonsItem(
    headline: String,
    imgPath: Int,
    imgDescription: String,
    itemId: Int,  // Unique ID for this item
    selectedItem: Int,  // ID of the selected item
    onSelectItem: (Int) -> Unit,  // Callback to update selection
    modifier: Modifier = Modifier,
    itemValue: String,
    onClick: (String) -> Unit,
) {
    val isClicked = itemId == selectedItem

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onSelectItem(itemId)
                onClick(itemValue)
            }
            .border(if (isClicked) 2.dp else 0.dp,
                if (isClicked) colorScheme.outline else Color.Transparent,
                shape = RoundedCornerShape(15.dp))

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
        var selectedSpecies by remember { mutableStateOf("Select Species") }

        Box(modifier = Modifier
            .clickable {
                expanded = true
                onDropdownTapped()
            }
            .fillMaxWidth()
            .height(45.dp)
            .border(width = 2.dp, color = colorScheme.outline, shape = RoundedCornerShape(12.dp)) // Apply border
            .clip(RoundedCornerShape(12.dp)), // Then clip to the same shape

            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 40.dp)

            ){

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

            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Arrow", modifier = Modifier.padding(start = 10.dp, end = 10.dp))

        }



        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            APIIconItem(
                "Location",
                "api value",
                R.drawable.placeholder,
                "location icon",
                modifier = Modifier.weight(1f),

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
fun AddPlantWateringContainer(
    dateState: String,
    setwaterFrequency: (Int) -> Unit,
    setDate: (String) -> Unit,
    waterInterval: Int
) {

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
            selectedDate = dateState,
            onDateChange = setDate
        )

        WateringFrequencySelector(
            "How frequent do you want to water your plant?", waterInterval, setwaterFrequency)
    }
    Spacer(modifier = Modifier.height(20.dp))

}

@Composable
fun WateringFrequencySelector(
    headline: String,
    waterInterval: Int,
    setwaterInterval: (Int) -> Unit
) {

    fun handleWaterIntervalChange(newWaterInterval: Int) {
        setwaterInterval(newWaterInterval)
    }

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
                    handleWaterIntervalChange(waterInterval - 1)
            })

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                H1Text(text = waterInterval.toString()) //Increase variable
                CopyBoldText(text = "days", colorScheme.primary) //Increase variable
            }

            PlusMinusButton(R.drawable.icon_plus, onClickLogic = {
                handleWaterIntervalChange(waterInterval + 1)
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
    imgPath: String?,
    capturedImgUri: Uri?,
    onClickLogic: () -> Unit
) {

    Log.e("capturedImgUri", capturedImgUri.toString())
    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .size(120.dp) // Set the size including the border
            .background(color = colorScheme.primary, shape = CircleShape)
            .clickable { onClickLogic() },
    ) {
        if (capturedImgUri.toString() != "") {
            Image(
                painter = rememberImagePainter(data = capturedImgUri),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(115.dp), // Clip the image to a circle shape
                contentScale = ContentScale.Crop
            )
        } else if (imgPath != "") {
            Image(
                painter = rememberImagePainter(imgPath),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(115.dp), // Clip the image to a circle shape
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.icon_camera_white),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(100.dp), // Clip the image to a circle shape
                contentScale = ContentScale.Fit
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}





