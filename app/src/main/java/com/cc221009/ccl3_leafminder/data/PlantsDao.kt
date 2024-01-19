package com.cc221009.ccl3_leafminder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cc221009.ccl3_leafminder.data.model.Plants
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantsDao {

    @Insert
    suspend fun insertPlant(plant: Plants)

    @Update
    suspend fun updatePlant(plant: Plants)

    @Delete
    suspend fun deletePlant(plant: Plants)

    @Query("SELECT * FROM plants")
    suspend fun getPlants(): List<Plants>
}