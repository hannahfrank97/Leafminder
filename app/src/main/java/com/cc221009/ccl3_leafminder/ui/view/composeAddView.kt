package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    Text(text = "AddView", fontSize = 50.sp)
    Column() {

        // Header
        Header("Add a new plant", R.drawable.icon_minus)

        // Profile Image
        Button(
            onClick = {
                //Logic for adding Image
            }
        ) {
            Image(painter = painterResource(id = R.drawable.placeholder), "Profile Picture")
        }

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
fun AddPlantInfoContainer() {
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

@Composable
fun AddParameterContainer(
        headline: String,
        content: @Composable () -> Unit
    ) {
    Column {
        Text(text = headline)
        Row() {
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

    //HEADLINE
    Text(text = "Species")

    DefaultTextField("Which type of plant is it?", "Select species")

    Row() {
        APIIconItem("Location", "api value", R.drawable.placeholder, "location icon")
        APIIconItem("Watering", "api value", R.drawable.placeholder, "watering icon")
        APIIconItem("Poisinousness", "api value", R.drawable.placeholder, "poisonousness icon")
    }
}

@Composable
fun APIIconItem(
    headline: String,
    apiValue: String,
    imgPath: Int,
    imgDescription: String
) {
    Column {
        Image(painter = painterResource(id = imgPath), imgDescription)
        Text(text = headline)
        Text(text = apiValue)
    }

}

// WATERING COMPONENTS

@Composable
fun AddPlantWateringContainer() {

    //HEADLINE
    Text(text = "Watering")

    DefaultTextField("Select the date of last watering", "Select date")

    WateringFrequencySelector("How frequent do you want to water your plant?")

}

@Composable
fun WateringFrequencySelector(
    headline: String,
) {
    Column {
        Text(text = headline)
        Row() {
            PlusMinusButton(R.drawable.icon_minus, onClickLogic = {
                // Define what should happen when the button is clicked
                println("Minus one")
            })

            Text(text = "20") //Increase variable
            Text(text = "days") //Increase variable

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
            contentDescription = "Back"
        )
    }
}




