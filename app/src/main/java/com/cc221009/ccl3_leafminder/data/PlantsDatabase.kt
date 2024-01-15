package com.cc221009.ccl3_leafminder.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cc221009.ccl3_leafminder.data.model.Plants

@Database(entities = [Plants::class], version = 1)
abstract class PlantsDatabase: RoomDatabase() {
    abstract val dao: PlantsDao
}

