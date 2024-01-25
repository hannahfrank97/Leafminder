package com.cc221009.ccl3_leafminder.data

import com.cc221009.ccl3_leafminder.R
import java.util.Locale

fun determineLocationIconFor(locationDescription: String): Int {
    val normalized = locationDescription.lowercase(Locale.ROOT)
    return when (normalized) {
        "full sun" -> R.drawable.location_light
        "part shade" -> R.drawable.location_half_shadow
        "part sun" -> R.drawable.location_half_light
        else -> R.drawable.location_half_light
    }
}

fun determineWateringIconFor(wateringDescription: String): Int {
    val normalized = wateringDescription.lowercase(Locale.ROOT)
    return when (normalized) {
        "minimum" -> R.drawable.watering_frequent
        "average" -> R.drawable.watering_normal
        "frequent" -> R.drawable.watering_frequent
        else -> R.drawable.watering_normal
    }
}

fun determinePoisonousnessIconFor(poisonousFlag: Int): Int {
    return when (poisonousFlag) {
        1 -> R.drawable.poisonous_true
        0 -> R.drawable.poisonous_false
        else -> R.drawable.poisonous_true
    }
}

fun textForPoisonunosnessFlag(poisonousFlag: Int): String {
    return when (poisonousFlag) {
        1 -> "Poisonous"
        0 -> "Non-poisonous"
        else -> "Unknown"
    }
}