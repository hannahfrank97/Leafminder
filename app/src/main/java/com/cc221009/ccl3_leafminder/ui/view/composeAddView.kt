package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(mainViewModel: MainViewModel, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Header
        Header("Add a new plant", R.drawable.icon_minus)

        // Profile Image
        PlantImage(R.drawable.placeholder, onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })

        // Textfield Name
        DefaultTextField("Name", "Name")
        
        AddPlantInfoContainer()

        AddPlantSpeciesContainer()

        AddPlantWateringContainer()

        PrimaryButton("Add Plant", onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })
    }

}

// INFO COMPONENTS

@Composable
fun AddPlantInfoContainer(
) {
    Column(
        modifier = Modifier
            .background(colorScheme.secondary)
            .padding(20.dp)
    ) {
        DefaultTextField("Since when do you have your plant?", "Select date")

        AddParameterContainer("size") {
            IconButtonsItem("small", R.drawable.placeholder, "small")
            IconButtonsItem("medium", R.drawable.placeholder, "medium")
            IconButtonsItem("large", R.drawable.placeholder, "large")
        }

        AddParameterContainer("location") {
            IconButtonsItem("light", R.drawable.placeholder, "light")
            IconButtonsItem("half-light", R.drawable.placeholder, "half-light")
            IconButtonsItem("half-shadow", R.drawable.placeholder, "half-shadow")
            IconButtonsItem("shadow", R.drawable.placeholder, "shadow")
        }

        AddParameterContainer("wellbeing") {
            IconButtonsItem("great", R.drawable.placeholder, "great")
            IconButtonsItem("okay", R.drawable.placeholder, "okay")
            IconButtonsItem("miserable", R.drawable.placeholder, "miserable")
        }
    }

}

@Composable
fun AddParameterContainer(
        headline: String,
        content: @Composable () -> Unit
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = headline)
        Row(

        ) {
            content()
        }
    }
}

@Composable
fun IconButtonsItem(
    headline: String,
    imgPath: Int,
    imgDescription: String
) {

    Button(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .height(60.dp),
        onClick = {
            //Logic for adding Image
        }
    ) {
        Column {
            Image(painter = painterResource(id = imgPath), imgDescription)
            Text(text = headline)
        }
    }
}

// API COMPONENTS

@Composable
fun AddPlantSpeciesContainer() {

    Column(
        modifier = Modifier
            .background(colorScheme.tertiary)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        //HEADLINE
        Text(text = "Species")

        DefaultTextField("Which type of plant is it?", "Select species")

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            APIIconItem("Location", "api value", R.drawable.placeholder, "location icon")
            APIIconItem("Watering", "api value", R.drawable.placeholder, "watering icon")
            APIIconItem("Poisinousness", "api value", R.drawable.placeholder, "poisonousness icon")
        }
    }
}

@Composable
fun APIIconItem(
    headline: String,
    apiValue: String,
    imgPath: Int,
    imgDescription: String
) {
    Column(
        modifier = Modifier
            .background(colorScheme.surface)
            .padding(20.dp)
            .fillMaxWidth(0.3f)
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imgPath),
            imgDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)
        Text(text = headline)
        Text(text = apiValue)
    }
}

// WATERING COMPONENTS

@Composable
fun AddPlantWateringContainer() {

    Column(
        modifier = Modifier
            .background(colorScheme.secondary)
            .padding(20.dp)
    ) {
        //HEADLINE
        Text(text = "Watering")

        DefaultTextField("Select the date of last watering", "Select date")

        WateringFrequencySelector("How frequent do you want to water your plant?")
    }
}

@Composable
fun WateringFrequencySelector(
    headline: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = headline)
        Row() {
            PlusMinusButton(R.drawable.icon_minus, onClickLogic = {
                // Define what should happen when the button is clicked
                println("Minus one")
            })

            Column {
                Text(text = "20") //Increase variable
                Text(text = "days") //Increase variable
            }

            PlusMinusButton(R.drawable.icon_plus, onClickLogic = {
                // Define what should happen when the button is clicked
                println("Plus one")
            })
        }
    }

}

@Composable
fun PlusMinusButton(
    imgPath: Int,
    onClickLogic: () -> Unit
) {
    Button(
        onClick = { onClickLogic() }
    ) {
        Icon(
            painter = painterResource(id = imgPath),
            contentDescription = "Back",
        )
    }
}

@Composable
fun PlantImage(
    imgPath: Int,
    onClickLogic: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .border(2.dp, colorScheme.outline, RoundedCornerShape(10.dp))
            .height(50.dp)
            .width(50.dp),
        onClick = { onClickLogic() }
    ) {
        Image(
            painter = painterResource(id = imgPath),
            "Profile Picture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)
    }
}





