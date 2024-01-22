package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.cc221009.ccl3_leafminder.data.needsToBeWatered
import com.cc221009.ccl3_leafminder.ui.view_model.AddPlantViewModel
import com.cc221009.ccl3_leafminder.ui.view_model.HomeUIState
import com.cc221009.ccl3_leafminder.ui.view_model.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    vm: HomeViewModel = viewModel(
        factory = HomeViewModel.provideFactory(
            PlantsRepository(
                getDatabase(LocalContext.current).dao
            )
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

        HomeHeaderContainer()

        PlantListOverview(state, navController)

        PlantDashboard(state, navController = navController)

    }


}

@Composable
fun HomeHeaderContainer() {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeHeaderContainerItem(
            "Current Plants",
            "25",
            R.drawable.graphics_blur_pot,
            MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        HomeHeaderContainerItem(
            "Species",
            "25",
            R.drawable.graphics_blur_leaf,
            MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        HomeHeaderContainerItem(
            "Needing Water",
            "25",
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
    navController: NavController) {
    val scrollState = rememberScrollState()

    Column() {
        H2Text(text = "Your Plants")

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),

        ) {
            state.plants.take(10).forEach { plant ->
                PlantItem(
                    navController,
                    plantName = plant.name,
                    species = "" ,
                    imgPath = plant.imagePath,
                    needsWater = false,
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

    Column() {
        H2Text(text = "Plants in need")

        Spacer(modifier = Modifier.height(15.dp))

        val plantNeedsWater = state.plants.filter { plant ->
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val wateringDateAsLocalDate = LocalDate.parse(plant.wateringDate, formatter)
            val wateringFrequencyAsInt = plant.wateringFrequency.toInt()
            needsToBeWatered(wateringDateAsLocalDate, wateringFrequencyAsInt)
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
                )
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Composable
fun HomeViewWateringNotification(
    navController: NavController,
    name: String,
    plantSpecies: String,
    imgPath: String,
) {
    Row(
        modifier = Modifier
            .clickable { navController.navigate(Screen.DetailView.route) }
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
                    .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape),
            ) {
                Image(
                    painter = rememberImagePainter(imgPath),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(60.dp) // Image size, smaller than the Box to create a border effect
                        .align(Alignment.Center) // Center the image inside the Box
                        .clip(CircleShape), // Clip the image to a circle shape
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .clickable {
                            //TODO create clicking button here
                        }
                        .clip(CircleShape)
                        .size(30.dp)
                        .align(Alignment.TopEnd) // Center the image inside the Box
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_waterdrop_small),
                        contentDescription = "Waterdrop",
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
            ) {
                Row {
                    CopyBoldText(text = name, MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(5.dp))
                    CopyItalicText(text = plantSpecies, MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(5.dp))
                CopyText(text = "Your plant needs water!")

            }

        }

        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .clip(CircleShape)
                .size(45.dp)
                .background(MaterialTheme.colorScheme.secondary),
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_tick),
                contentDescription = "Waterdrop",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(25.dp)
            )
        }

    }

    Spacer(modifier = Modifier.height(10.dp))
}
