package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
fun HomeView(navController: NavController){

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
            R.drawable.placeholder,
            MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(20.dp))

        HomeHeaderContainerItem(
            "Species",
            "25",
            R.drawable.placeholder,
            MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(20.dp))

        HomeHeaderContainerItem(
            "Needing Water",
            "25",
            R.drawable.placeholder,
            MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(40.dp))


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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(backgroundColor)
            .then(modifier)
    ) {

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

        Text(text = headline)

    }

}

// PLAST LIST COMPOSABLE
@Composable
fun PlantListOverview() {

    Column() {
        Text(text = "Your Plants")

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            PlantItem("Linda", null, R.drawable.placeholder)
            PlantItem("Linda", null, R.drawable.placeholder)
            PlantItem("Linda", null, R.drawable.placeholder)
            PlantItem("Linda", null, R.drawable.placeholder)
        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}



// PLANT DASHBOARD COMPOSABLE

@Composable
fun PlantDashboard() {

    Column() {
        Text(text = "Plants in need")

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            PlantItem("Linda", null, R.drawable.placeholder)
            PlantItem("Linda", null, R.drawable.placeholder)
            PlantItem("Linda", null, R.drawable.placeholder)
            PlantItem("Linda", null, R.drawable.placeholder)
        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}