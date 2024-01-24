package com.cc221009.ccl3_leafminder.ui.view

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cc221009.ccl3_leafminder.data.SpeciesDetails
import com.cc221009.ccl3_leafminder.data.determineLocationIconFor
import com.cc221009.ccl3_leafminder.data.determinePoisonousnessIconFor
import com.cc221009.ccl3_leafminder.data.determineWateringIconFor
import com.cc221009.ccl3_leafminder.ui.view_model.APISpeciesItem

@Composable
fun SpeciesChooser(
    speciesItems: List<APISpeciesItem>,
    selectedSpeciesDetails: SpeciesDetails?,
    onSpeciesRequested: () -> Unit,
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        //HEADLINE
        H3Text(text = "Species")

        var expanded by remember { mutableStateOf(false) }
        var selectedSpecies by remember { mutableStateOf("Select Species") }

        Box(
            modifier = Modifier
                .clickable {
                    expanded = true
                    onSpeciesRequested()
                }
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                ) // Apply border
                .clip(RoundedCornerShape(12.dp)), // Then clip to the same shape

            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 40.dp)

            ) {

                CopyText(selectedSpecies)
            }
            DropdownMenu(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(MaterialTheme.colorScheme.background),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                speciesItems.forEach { speciesItem ->
                    DropdownMenuItem(onClick = {
                        selectedSpecies = speciesItem.speciesName
                        expanded = false
                    }) {
                        CopyText(speciesItem.speciesName)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )

        }

        if (selectedSpeciesDetails != null) {
            SpeciesDetailsDisplay(selectedSpeciesDetails)
        }

    }
    Spacer(modifier = Modifier.height(30.dp))

}

@Composable
private fun SpeciesDetailsDisplay(speciesDetails: SpeciesDetails) {
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
            determinePoisonousnessIconFor(speciesDetails.poisonousnes.toString()),
            "poisonousness icon",
            modifier = Modifier.weight(1f)
        )
    }
}