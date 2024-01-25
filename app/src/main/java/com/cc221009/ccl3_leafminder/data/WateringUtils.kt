package com.cc221009.ccl3_leafminder.data

import com.cc221009.ccl3_leafminder.data.model.Plant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun calculateNextWateringDate(lastWatered: LocalDate, wateringFrequency: Int): LocalDate {
    return lastWatered.plusDays(wateringFrequency.toLong())
}

fun calculateDaysUntilNextWatering(lastWatered: LocalDate, wateringFrequency: Int): Int {
    val nextWateringDate = calculateNextWateringDate(lastWatered, wateringFrequency)
    return ChronoUnit.DAYS.between(LocalDate.now(), nextWateringDate).toInt()
}

fun needsToBeWatered(lastWatered: LocalDate, wateringFrequency: Int): Boolean {
    return calculateDaysUntilNextWatering(lastWatered, wateringFrequency) <= 0
}


fun checkIfNeedsWater(wateringFrequency: String, wateringDate: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val wateringDateAsLocalDate = LocalDate.parse(wateringDate, formatter)
    val wateringFrequencyAsInt = wateringFrequency.toInt()
    val needsWater = needsToBeWatered(wateringDateAsLocalDate, wateringFrequencyAsInt)

    return needsWater
}

fun checkAllIfNeedsWater(plants: List<Plant>): List<Plant> {
    val plantNeedsWater = plants.filter { plant ->
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val wateringDateAsLocalDate = LocalDate.parse(plant.wateringDate, formatter)
        val wateringFrequencyAsInt = plant.wateringFrequency.toInt()
        needsToBeWatered(wateringDateAsLocalDate, wateringFrequencyAsInt)
    }
    return plantNeedsWater
}



