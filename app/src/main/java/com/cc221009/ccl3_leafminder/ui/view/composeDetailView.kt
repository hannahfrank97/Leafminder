package com.cc221009.ccl3_leafminder.ui.view

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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.calculateDaysUntilNextWatering
import com.cc221009.ccl3_leafminder.data.checkIfNeedsWater
import com.cc221009.ccl3_leafminder.data.makePlantRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view_model.DetailViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    plantId: Int,
    vm: DetailViewModel = viewModel(
        factory = DetailViewModel.provideFactory(
            makePlantRepository(LocalContext.current)
        )

    ),

    navController: NavController
) {
    val state by vm.uiState.collectAsState()
    val plantToDisplay = state.fullPlant

    if (plantToDisplay == null) {

        LaunchedEffect(key1 = plantId) {
            state.loadPlant(plantId)
        }
        Text(text = "Loading...")
        return

    }
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val currentDate = LocalDate.now()
    var daysSurvived = 0
    var nextWateringIn = 0

    try {
        val acquisitionDate = LocalDate.parse(plantToDisplay.plant.date, formatter)
        daysSurvived = ChronoUnit.DAYS.between(acquisitionDate, currentDate).toInt()
    } catch (e: Exception) {
        Log.e("DetailView", "Error calculating days survived: ${e.message}")
    }

    try {
        val lastWateringDate = LocalDate.parse(plantToDisplay.plant.wateringDate, formatter)
        nextWateringIn = calculateDaysUntilNextWatering(
            lastWateringDate,
            plantToDisplay.plant.wateringFrequency.toInt()
        )
    } catch (e: Exception) {
        Log.e("DetailView", "Error calculating next watering: ${e.message}")
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
                navController.popBackStack()
            },
            rightIconLogic = {
                navController.navigate("EditView/${plantId}")
            })

        PlantDetailImage(plantToDisplay.plant, "R..placeholder", "plant species")

        Spacer(modifier = Modifier.height(20.dp))

        var isClicked: Boolean by remember { mutableStateOf(false) }
        var needsWater: Boolean by remember { mutableStateOf(false) }

        needsWater = checkIfNeedsWater(
            state.fullPlant?.plant!!.wateringFrequency,
            state.fullPlant?.plant!!.wateringDate
        )

        if (needsWater) {
            PlantDetail_WateringNotification(
                state.fullPlant?.plant!!.name,
                isClicked,
                clickLogic = {
                    isClicked = true
                },
                updateWateringDate = {
                    println("INSIDE THE UPDATEWATERINGDATE")
                    state.updateWateringDate(state.fullPlant?.plant!!.id)
                    needsWater = false
                }
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        PlantDetailGeneralContainer(plantToDisplay.plant)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpecificInfoContainer(
                plantToDisplay.plant.wateringFrequency,
                "Water Interval",
                R.drawable.graphics_blur_calendar,
                colorScheme.secondaryContainer,
                false,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            SpecificInfoContainer(
                nextWateringIn.toString(),
                "Next watering in",
                R.drawable.graphics_blur_waterdrop,
                colorScheme.secondaryContainer,
                needsWater,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            SpecificInfoContainer(
                daysSurvived.toString(),
                "your plant survived",
                null,
                colorScheme.tertiaryContainer,
                false,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        H2Text("Plant specific information")
        Spacer(modifier = Modifier.height(20.dp))

        if (plantToDisplay.speciesDetails != null)
            SpeciesDetailsDisplay(speciesDetails = plantToDisplay.speciesDetails)
    }
}

@Composable
fun PlantDetailImage(
    plant: Plant,
    imgPath: String?,
    plantSpecies: String
) {
    Box(
        modifier = Modifier
            .size(181.dp) // Set the size including the border
            .background(color = colorScheme.primary, shape = CircleShape)
    ) {

        if (plant.imagePath != "") {
            Image(
                painter = rememberImagePainter(plant.imagePath),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(175.dp), // Clip the image to a circle shape
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.graphics_placeholder_plant),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center) // Center the image inside the Box
                    .size(175.dp)
                    .background(colorScheme.surface), // Clip the image to a circle shape
                contentScale = ContentScale.Crop
            )
        }


    }
    Spacer(modifier = Modifier.height(20.dp))

    H1Text(text = plant.name)

    CopyItalicText(text = plantSpecies, colorScheme.primary)
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
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(10.dp))
        CopyText(text = text)
    }
}

@Composable
fun SpecificInfoContainer(
    text: String,
    headline: String,
    imgPath: Int?,
    backgroundColor: Color,
    needsWater: Boolean,
    modifier: Modifier = Modifier,
) {

    val borderColor = if (needsWater) colorScheme.secondary else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .height(150.dp)
            .background(backgroundColor)
            .border(width = 3.dp, color = borderColor, RoundedCornerShape(15.dp))
            .padding(10.dp)
            .then(modifier)
    ) {

        H4Text(text = headline)

        Box(
            contentAlignment = Alignment.Center,
        ) {

            if (imgPath != null) {
                Image(
                    painter = painterResource(id = imgPath),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (text.toInt() <= 0) {
                    H1Text(text = "0")
                } else {
                    H1Text(text = text)
                }
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


@Composable
fun PlantDetail_WateringNotification(
    text: String,
    isClicked: Boolean,
    clickLogic: () -> Unit,
    updateWateringDate: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        LaunchedEffect(isClicked) {
            if (isClicked) {
                delay(500L)
                updateWateringDate()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(colorScheme.secondaryContainer)
                .border(width = 3.dp, color = colorScheme.secondary, RoundedCornerShape(15.dp))
                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_waterdrop_large_blur),
                contentDescription = "Waterdrop",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .padding(end = 15.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Have you already watered ${text}?",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Left
                    )
                )
                CopyText(text = "Your plant needs water!")
            }

            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable {
                        clickLogic()
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
        }
    }
}