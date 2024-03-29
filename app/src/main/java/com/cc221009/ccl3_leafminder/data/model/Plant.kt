package com.cc221009.ccl3_leafminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    val name: String,
    val date: String,
    val size: String,
    val location: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,
    val apiId: Int?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
