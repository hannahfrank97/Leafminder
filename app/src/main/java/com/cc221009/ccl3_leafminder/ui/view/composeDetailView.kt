package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Header("Add a new plant", R.drawable.icon_edit)

        PlantDetailImage(R.drawable.placeholder, "Linda", "Species")

        Spacer(modifier = Modifier.height(20.dp))

        PlantDetailGeneralContainer()

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpecificInfoContainer("Water", "specific info", R.drawable.placeholder, colorScheme.secondary,
                modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(20.dp))

            SpecificInfoContainer("location", "specific info", R.drawable.placeholder, colorScheme.secondary,
                modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(20.dp))

            SpecificInfoContainer("poisonous", "specific info", R.drawable.placeholder, colorScheme.secondary,
                modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Plant specific information")
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ApiInfoItem("watering", "specific info", R.drawable.placeholder, colorScheme.secondary)
            ApiInfoItem("location", "specific info", R.drawable.placeholder, colorScheme.secondary)
            ApiInfoItem("poisonous", "specific info", R.drawable.placeholder, colorScheme.secondary)
        }
    }
}

@Composable
fun PlantDetailImage(
    imgPath: Int,
    plantName: String,
    plantSpecies: String
) {
    Image(
        painter = painterResource(id = imgPath),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .height(200.dp)
            .width(200.dp),
        contentScale = ContentScale.Crop)

    H1Text(text = plantName)

    Text(text = plantSpecies)
}

@Composable
fun PlantDetailGeneralContainer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        PlantDetailItem(R.drawable.placeholder, "big", null)
        Spacer(modifier = Modifier.width(20.dp))
        PlantDetailItem(R.drawable.placeholder,"great", null)
        Spacer(modifier = Modifier.width(20.dp))
        PlantDetailItem(R.drawable.placeholder,"light", null)
    }
}

@Composable
fun PlantDetailItem(
    imgPath: Int,
    text: String,
    textDetail: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imgPath),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            contentScale = ContentScale.Crop)

        Text(text = text)

        // Conditionally display textDetail if it's not null
        textDetail?.let {
            Text(text = it)
        }


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
                .background(backgroundColor)
                .then(modifier)
        ) {

            Text(text = headline)

            Box(
                contentAlignment = Alignment.Center,
            ){
                Text(text = text)

                Image(
                    painter = painterResource(id = imgPath),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    contentScale = ContentScale.Crop)
            }
    }

}

@Composable
fun ApiInfoItem(
    text: String,
    textDetail: String?,
    imgPath: Int,
    backgroundColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(backgroundColor)
    ) {
        Image(
            painter = painterResource(id = imgPath),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            contentScale = ContentScale.Crop)

        Text(text = text)

        // Conditionally display textDetail if it's not null
        textDetail?.let {
            Text(text = it)
        }
    }
}