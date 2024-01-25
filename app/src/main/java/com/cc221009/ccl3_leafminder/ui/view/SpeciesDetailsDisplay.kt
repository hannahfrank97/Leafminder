package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cc221009.ccl3_leafminder.data.SpeciesDetails
import com.cc221009.ccl3_leafminder.data.determineLocationIconFor
import com.cc221009.ccl3_leafminder.data.determinePoisonousnessIconFor
import com.cc221009.ccl3_leafminder.data.determineWateringIconFor

@Composable
fun SpeciesDetailsDisplay(speciesDetails: SpeciesDetails) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        APIIconItem(
            "Location",
            "api value",
            determineLocationIconFor(speciesDetails.sunlight.first()),
            "location icon",
            modifier = Modifier.weight(1f),
        )
        APIIconItem(
            "Watering",
            "api value",
            determineWateringIconFor(speciesDetails.watering),
            "watering icon",
            modifier = Modifier.weight(1f)
        )
        APIIconItem(
            "Poisinousness",
            "api value",
            determinePoisonousnessIconFor(speciesDetails.poisonous_to_humans),
            "poisonousness icon",
            modifier = Modifier.weight(1f)
        )
    }
}