package com.cc221009.ccl3_leafminder.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
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
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.getDatabase
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view_model.DetailViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    plantId: Int,
    vm: DetailViewModel = viewModel(
        factory = DetailViewModel.provideFactory(
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
        }
        Text(text = "Loading...")
        return

    }
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val daysSurvived: Int

    try {
        val acquisitionDate = LocalDate.parse(state.plant!!.date, formatter)
        val currentDate = LocalDate.now()
        daysSurvived = ChronoUnit.DAYS.between(acquisitionDate, currentDate).toInt()
    } catch (e: Exception) {
        Log.e("DetailView", "Error parsing date: ${e.message}")
        return
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Header(
            null, R.drawable.icon_edit,
            leftIconLogic = {
                navController.navigate(Screen.HomeView.route)
            },
            rightIconLogic = {
                navController.navigate("EditView/${plantId}")
            })

        PlantDetailImage(state.plant!!, R.drawable.placeholder, "plant species")

        Spacer(modifier = Modifier.height(20.dp))

        PlantDetailGeneralContainer(state.plant!!)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpecificInfoContainer(
                state.plant!!.wateringFrequency,
                "Water Interval",
                R.drawable.graphics_blur_calendar,
                colorScheme.secondaryContainer,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            SpecificInfoContainer(
                "3",
                "Next watering in",
                R.drawable.graphics_blur_waterdrop,
                colorScheme.secondaryContainer,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            SpecificInfoContainer(
                daysSurvived.toString(),
                "your plant survived",
                R.drawable.placeholder,
                colorScheme.tertiaryContainer,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        H2Text("Plant specific information")
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ApiInfoItem(
                "watering",
                "specific info",
                R.drawable.watering_normal,
                colorScheme.surface,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            ApiInfoItem(
                "location",
                "specific info",
                R.drawable.location_light,
                colorScheme.surface,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            ApiInfoItem(
                "poisonous",
                "specific info",
                R.drawable.poisonous_true,
                colorScheme.surface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PlantDetailImage(
    plant: Plant,
    imgPath: Int,
    plantSpecies: String
) {
    Box(
        modifier = Modifier
            .size(180.dp) // Set the size including the border
            .background(color = colorScheme.primary, shape = CircleShape)
    ) {

        Image(
            painter = painterResource(id = imgPath),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.Center) // Center the image inside the Box
                .size(175.dp), // Clip the image to a circle shape
            contentScale = ContentScale.Crop
        )


    }

    H1Text(text = plant.name)

    CopyItalicText(text = plantSpecies, colorScheme.primary)
    Spacer(modifier = Modifier.height(20.dp))

}

@Composable
fun PlantDetailGeneralContainer(
    plant: Plant
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(colorScheme.tertiaryContainer)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val drawableResSize = when (plant.size) {
            "small" -> R.drawable.plant_base_small
            "medium" -> R.drawable.plant_base_medium
            else -> R.drawable.plant_base_large
        }

        val drawableResWellbeing = when (plant.wellbeing) {
            "great" -> R.drawable.wellbeing_good
            "okay" -> R.drawable.wellbeing_okay
            else -> R.drawable.wellbeing_bad
        }

        val drawableResLocation = when (plant.location) {
            "light" -> R.drawable.location_light
            "half-light" -> R.drawable.location_half_light
            "half-shadow" -> R.drawable.location_half_shadow
            else -> R.drawable.location_shadow
        }

        PlantDetailItem(drawableResSize, plant.size)
        Spacer(modifier = Modifier.width(20.dp))
        PlantDetailItem(drawableResWellbeing, plant.wellbeing)
        Spacer(modifier = Modifier.width(20.dp))
        PlantDetailItem(drawableResLocation, plant.location)

    }


}

@Composable
fun PlantDetailItem(
    imgPath: Int,
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = imgPath),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            contentScale = ContentScale.Crop
        )

        CopyText(text = text)
    }
}

@Composable
fun SpecificInfoContainer(
    text: String,
    headline: String,
    imgPath: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxHeight()
            .background(backgroundColor)
            .padding(20.dp)
            .then(modifier)
    ) {

        H4Text(text = headline)

        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = imgPath),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Fit
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                H1Text(text = text)
                Text(text = "days")
            }
        }
    }

}

@Composable
fun ApiInfoItem(
    text: String,
    textDetail: String?,
    imgPath: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .padding(top = 20.dp, bottom = 20.dp)
            .then(modifier),

        ) {
        Image(
            painter = painterResource(id = imgPath),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(10.dp))


        H4Text(text = text)

        // Conditionally display textDetail if it's not null
        textDetail?.let {
            CopyText(text = it)
        }
    }
}