package com.cc221009.ccl3_leafminder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cc221009.ccl3_leafminder.data.model.Plant

@Database(entities = [Plant::class], version = 3)
abstract class PlantsDatabase : RoomDatabase() {
    abstract val dao: PlantsDao
}

private fun makeDatabase(context: Context): PlantsDatabase {
    return Room.databaseBuilder(context, PlantsDatabase::class.java, "plantDatabase.db")
        .fallbackToDestructiveMigration()
        .build()
}

private var db: PlantsDatabase? = null

fun getDatabase(context: Context): PlantsDatabase {
    if (db == null) db = makeDatabase(context)
    return db!!
}
