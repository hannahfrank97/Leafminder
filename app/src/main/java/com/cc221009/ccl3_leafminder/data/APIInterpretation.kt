package com.cc221009.ccl3_leafminder.data

import com.cc221009.ccl3_leafminder.R
import java.util.Locale

fun determineLocationIconFor(locationDescription:String): Int {
    val normalized = locationDescription.lowercase(Locale.ROOT)
    return when (normalized) {
        "full sun" -> R.drawable.location_light
        "part shade" -> R.drawable.location_light
        "part sun" -> R.drawable.location_light
        else -> R.drawable.placeholder
    }
}

fun determineWateringIconFor(wateringDescription:String): Int {
    val normalized = wateringDescription.lowercase(Locale.ROOT)
    return when (normalized) {
        "minimum" -> R.drawable.watering_frequent
        "average" -> R.drawable.watering_normal
        "frequent" -> R.drawable.watering_frequent
        else -> R.drawable.placeholder
    }
}

fun determinePoisonousnessIconFor(poisonousnessDescription:String): Int {
    val normalized = poisonousnessDescription.lowercase(Locale.ROOT)
    return when (normalized) {
        "true" -> R.drawable.poisonous_true
        "false" -> R.drawable.poisonous_false
        else -> R.drawable.placeholder
    }
}