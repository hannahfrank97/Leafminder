package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun HomeView(mainViewModel: MainViewModel, navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        HomeHeaderContainer()

        PlantListOverview()

        PlantDashboard()

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
            modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(10.dp))

        HomeHeaderContainerItem(
            "Species",
            "25",
            R.drawable.graphics_blur_leaf,
            MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(10.dp))

        HomeHeaderContainerItem(
            "Needing Water",
            "25",
            R.drawable.graphics_blur_waterdrop,
            MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.weight(1f))
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
                ){
                Image(
                    painter = painterResource(id = imgPath),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(70.dp)
                        .width(80.dp),
                    contentScale = ContentScale.Crop)
                H1Text(text = text)
            }

                H4Text(text = headline)
            }
}

// PLAST LIST COMPOSABLE
@Composable
fun PlantListOverview() {
    val scrollState = rememberScrollState()

    Column() {
        H2Text(text = "Your Plants")

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
        ) {
            PlantItem("Linda", null, R.drawable.placeholder, true)
            PlantItem("Linda", null, R.drawable.placeholder,false)
            PlantItem("Linda", null, R.drawable.placeholder,false)
            PlantItem("Linda", null, R.drawable.placeholder,false)
            PlantItem("Linda", null, R.drawable.placeholder,false)
            PlantItem("Linda", null, R.drawable.placeholder,false)
            PlantItem("Linda", null, R.drawable.placeholder,false)
            PlantItem("Linda", null, R.drawable.placeholder,false)
        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}



// PLANT DASHBOARD COMPOSABLE

@Composable
fun PlantDashboard() {

    Column() {
        H2Text(text = "Plants in need")

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            HomeViewWateringNotification("Linda", "plant species", R.drawable.placeholder)
            HomeViewWateringNotification("Linda", "plant species", R.drawable.placeholder)
            HomeViewWateringNotification("Linda", "plant species", R.drawable.placeholder)
        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Composable
fun HomeViewWateringNotification(
    name: String,
    plantSpecies: String,
    imgPath: Int,
) {
    Row(
        modifier = Modifier
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
                    painter = painterResource(id = imgPath),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(60.dp) // Image size, smaller than the Box to create a border effect
                        .align(Alignment.Center) // Center the image inside the Box
                        .clip(CircleShape), // Clip the image to a circle shape
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
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
                .background(MaterialTheme.colorScheme.secondary)
                ,
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
