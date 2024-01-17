package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(mainViewModel: MainViewModel, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Header
        Header("Edit this plant", R.drawable.icon_delete)

        // Profile Image
        PlantImage(R.drawable.placeholder, onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })

        // Textfield Name
        DefaultTextField("Name", "Name",
            text = TextFieldValue(""),
            onValueChange = {}
        )

        AddPlantInfoContainer()

        AddPlantSpeciesContainer(speciesNames = emptyList())

        AddPlantWateringContainer()

        PrimaryButton("Save Changes", onClickLogic = {
            // Define what should happen when the button is clicked
            println("Button was clicked")
        })
    }

}