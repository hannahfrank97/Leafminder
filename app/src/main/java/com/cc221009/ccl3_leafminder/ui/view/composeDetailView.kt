package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(mainViewModel: MainViewModel, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Header(null, R.drawable.icon_edit)

        PlantDetailImage(R.drawable.placeholder, "Linda", "Species")

        Spacer(modifier = Modifier.height(20.dp))

        PlantDetailGeneralContainer()

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpecificInfoContainer("14", "Water Interval", R.drawable.graphics_blur_calendar, colorScheme.secondaryContainer,
                modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(10.dp))

            SpecificInfoContainer("3", "Next watering in", R.drawable.graphics_blur_waterdrop, colorScheme.secondaryContainer,
                modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(10.dp))

            SpecificInfoContainer("234", "your plant survived", R.drawable.placeholder, colorScheme.tertiaryContainer,
                modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        H2Text("Plant specific information")
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ApiInfoItem("watering", "specific info", R.drawable.watering_normal, colorScheme.surface, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            ApiInfoItem("location", "specific info", R.drawable.location_light, colorScheme.surface, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            ApiInfoItem("poisonous", "specific info", R.drawable.poisonous_true, colorScheme.surface, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PlantDetailImage(
    imgPath: Int,
    plantName: String,
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

    H1Text(text = plantName)

    CopyItalicText(text = plantSpecies, colorScheme.primary)
    Spacer(modifier = Modifier.height(20.dp))

}

@Composable
fun PlantDetailGeneralContainer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(colorScheme.tertiaryContainer)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        PlantDetailItem(R.drawable.plant_base_small, "small")
        Spacer(modifier = Modifier.width(20.dp))
        PlantDetailItem(R.drawable.wellbeing_okay,"okay")
        Spacer(modifier = Modifier.width(20.dp))
        PlantDetailItem(R.drawable.location_half_shadow,"half-shadow")
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
            contentScale = ContentScale.Crop)

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
            ){
                Image(
                    painter = painterResource(id = imgPath),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Fit)

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
            contentScale = ContentScale.Fit)

        Spacer(modifier = Modifier.height(10.dp))


        H4Text(text = text)

        // Conditionally display textDetail if it's not null
        textDetail?.let {
            CopyText(text = it)
        }
    }
}