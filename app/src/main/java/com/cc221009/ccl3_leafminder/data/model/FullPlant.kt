package com.cc221009.ccl3_leafminder.data.model

import com.cc221009.ccl3_leafminder.data.SpeciesDetails

data class FullPlant(
    val plant: Plant,
    val speciesDetails: SpeciesDetails?,
)