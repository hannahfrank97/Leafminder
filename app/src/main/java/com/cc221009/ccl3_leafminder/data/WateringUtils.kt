package com.cc221009.ccl3_leafminder.data

import com.cc221009.ccl3_leafminder.data.model.Plant
import java.time.LocalDate
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



