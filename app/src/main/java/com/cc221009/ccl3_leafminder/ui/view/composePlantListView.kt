package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.cc221009.ccl3_leafminder.ui.view_model.PlantListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantListView(
    vm: PlantListViewModel = viewModel(
        factory = PlantListViewModel.provideFactory(
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
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Header("Your Plants", null, leftIconLogic = {
            navController.navigate(Screen.HomeView.route)
        }, rightIconLogic = {
            navController.navigate(Screen.EditView.route)
        })

        Spacer(modifier = Modifier.height(30.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = {
                items(state.plants) { plant ->
                    PlantListItem(
                        navController,
                        plant = plant,
                        species = "Species",//API call to get species name
                        needsWater = false,
                    )
                }
            }
        )

    }

}

@Composable

fun PlantListItem(
    navController: NavController,
    species: String?,
    needsWater: Boolean,
    plant: Plant,

    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate("DetailView/${plant.id}")
            }
    ) {

        val borderColor =
            if (needsWater) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .size(80.dp) // Set the size including the border
                .background(color = borderColor, shape = CircleShape),
        ) {
            if (plant.imagePath != "") {
                Image(
                    painter = rememberImagePainter(plant.imagePath),
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
                        .size(75.dp) // Image size, smaller than the Box to create a border effect
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
                        .size(35.dp)
                        .align(Alignment.TopEnd) // Center the image inside the Box
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_waterdrop_small),
                        contentDescription = "Waterdrop",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(5.dp))
        CopyText(text = plant.name)

        // Conditionally display textDetail if it's not null
        species?.let {
            CopyItalicText(text = it, MaterialTheme.colorScheme.outline)

        }

        Spacer(modifier = Modifier.height(10.dp))

    }
}


