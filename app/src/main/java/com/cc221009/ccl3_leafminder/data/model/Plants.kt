package com.cc221009.ccl3_leafminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plants(
    val name: String,
    val date: String,
    val size: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
)
