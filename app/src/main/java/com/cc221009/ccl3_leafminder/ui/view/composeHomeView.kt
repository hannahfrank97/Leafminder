package com.cc221009.ccl3_leafminder.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.cc221009.ccl3_leafminder.data.checkAllIfNeedsWater
import com.cc221009.ccl3_leafminder.data.checkIfNeedsWater
import com.cc221009.ccl3_leafminder.data.makePlantRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view_model.HomeUIState
import com.cc221009.ccl3_leafminder.ui.view_model.HomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    vm: HomeViewModel = viewModel(
        factory = HomeViewModel.provideFactory(
            makePlantRepository(LocalContext.current)

        )
    ),

    navController: NavController
) {

    val state by vm.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        state.seeAllPlants()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        HomeHeaderContainer(state.plants)

        PlantListOverview(state, navController)

        PlantDashboard(state = state, navController = navController)

    }


}

@Composable
fun HomeHeaderContainer(
    plantsList: List<Plant>,
) {
    fun List<Plant>.speciesCount(): Int {
        val uniqueSpecies = this.map { it.apiId }.toSet()
        return uniqueSpecies.size
    }

    val listCount: String = plantsList.count().toString()
    var listSpeciesCount: String = plantsList.speciesCount().toString()
    val plantsNeedingWater: String = checkAllIfNeedsWater(plantsList).count().toString()


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeHeaderContainerItem(
            "Current Plants",
            listCount,
            R.drawable.graphics_blur_pot,
            MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        HomeHeaderContainerItem(
            "Species",
            listSpeciesCount,
            R.drawable.graphics_blur_leaf,
            MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        HomeHeaderContainerItem(
            "Needing Water",
            plantsNeedingWater,
            R.drawable.graphics_blur_waterdrop,
            MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))


}

@Composable
fun HomeHeaderContainerItem(
    headline: String,
    text: String,
    imgPath: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .padding(10.dp)
            .height(120.dp)
            .then(modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = imgPath),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .height(70.dp)
                    .width(80.dp),
                contentScale = ContentScale.Crop
            )
            H1Text(text = text)
        }

        H4Text(text = headline)
    }
}

// PLAST LIST COMPOSABLE
@Composable
fun PlantListOverview(
    state: HomeUIState,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Column() {
        H2Text(text = "Your Plants")

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),

            ) {
            AddPlantItem(navController)

            state.plants.take(10).forEach { plant ->
                PlantItem(
                    navController,
                    plantId = plant.id,
                    plantName = plant.name,
                    species = "",
                    imgPath = plant.imagePath,
                    wateringDate = plant.wateringDate,
                    wateringFrequency = plant.wateringFrequency
                )
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}


// PLANT DASHBOARD COMPOSABLE

@Composable
fun PlantDashboard(
    state: HomeUIState,
    navController: NavController,
) {
    val plantNeedsWater = checkAllIfNeedsWater(state.plants)
    val plantNeedsWaterCount = plantNeedsWater.count()

    Column() {
        H2Text(text = "Plants in need (${plantNeedsWaterCount})")

        Spacer(modifier = Modifier.height(15.dp))



        if (plantNeedsWater.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.graphics_plant_arrangment),
                    contentDescription = "Graphic of Plants",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(20.dp))

                CopyBoldText(
                    text = "No plants in need right now",
                    color = MaterialTheme.colorScheme.outline
                )

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            plantNeedsWater.forEach { plant ->
                HomeViewWateringNotification(
                    navController = navController,
                    name = plant.name,
                    plantSpecies = "",
                    imgPath = plant.imagePath,
                    plantId = plant.id,
                    state = state
                )
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Composable
fun HomeViewWateringNotification(
    navController: NavController,
    plantId: Int,
    name: String,
    plantSpecies: String,
    imgPath: String,
    state: HomeUIState,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isClicked: Boolean by remember { mutableStateOf(false) }

        LaunchedEffect(isClicked) {
            if (isClicked) {
                Log.d("Debug", "Waterdrop clicked for plant ID: $plantId")
                delay(500L) // Delay for 1 second
                state.updateWateringDate(plantId)
                isClicked = false // Reset the click state
            }
        }

        Box(
            modifier = Modifier
                .padding(end = 15.dp)
                .clickable {
                    isClicked = true
                }
                .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(15.dp))
                .background(if (isClicked) MaterialTheme.colorScheme.secondary else Color.Transparent)
                .size(30.dp)
                .padding(5.dp)

        ) {
            if (isClicked) {
                Image(
                    painter = painterResource(id = R.drawable.icon_tick),
                    contentDescription = "tick",
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate("DetailView/${plantId}")
                }
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(65.dp) // Set the size including the border
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        ),
                ) {
                    if (imgPath != "") {
                        Image(
                            painter = rememberImagePainter(imgPath),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(60.dp) // Image size, smaller than the Box to create a border effect
                                .align(Alignment.Center) // Center the image inside the Box
                                .clip(CircleShape), // Clip the image to a circle shape
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.graphics_placeholder_plant),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center) // Center the image inside the Box
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface), // Clip the image to a circle shape
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clickable {

                            }
                            .clip(CircleShape)
                            .size(25.dp)
                            .align(Alignment.TopEnd) // Center the image inside the Box
                            .background(MaterialTheme.colorScheme.secondary),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_exclamation_mark),
                            contentDescription = "Waterdrop",
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.Center)
                        )
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp),

                    ) {

                    CopyBoldText(text = "${name} needs water!", MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.height(5.dp))
                    CopyText(text = "Have you watered your plant?")

                }

            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}


@Composable
fun PlantItem(
    navController: NavController,
    plantId: Int,
    plantName: String,
    species: String?,
    imgPath: String,
    wateringDate: String, // Assuming this is in "dd.MM.yyyy" format
    wateringFrequency: String // Assuming this is a String that can be converted to Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(end = 20.dp)
            .clickable {
                navController.navigate("DetailView/${plantId}")
            }
    ) {

        val needsWater = checkIfNeedsWater(wateringFrequency, wateringDate)

        val borderColor =
            if (needsWater) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

        Box(
            modifier = Modifier
                .size(80.dp) // Set the size including the border
                .background(color = borderColor, shape = CircleShape),
        ) {

            if (imgPath != "") {
                Image(
                    painter = rememberImagePainter(imgPath),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(75.dp) // Image size, smaller than the Box to create a border effect
                        .align(Alignment.Center) // Center the image inside the Box
                        .clip(CircleShape), // Clip the image to a circle shape
                    contentScale = ContentScale.Crop,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.graphics_placeholder_plant),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.Center) // Center the image inside the Box
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface), // Clip the image to a circle shape
                    contentScale = ContentScale.Crop,
                )
            }

            if (needsWater) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(25.dp)
                        .align(Alignment.TopEnd) // Center the image inside the Box
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_exclamation_mark),
                        contentDescription = "Waterdrop",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        CopyText(text = plantName)

        // Conditionally display textDetail if it's not null
        species?.let {
            CopyItalicText(text = it, MaterialTheme.colorScheme.outline)

        }
    }
}

@Composable
fun AddPlantItem(
    navController: NavController,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(end = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .size(80.dp) // Set the size including the border
                .clip(shape = CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .clickable {
                    navController.navigate("AddView")
                },
        ) {
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .align(Alignment.Center)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_plus),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(30.dp) // Image size, smaller than the Box to create a border effect
                        .align(Alignment.Center) // Center the image inside the Box
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        CopyText(text = "Add Plant")

    }
}
