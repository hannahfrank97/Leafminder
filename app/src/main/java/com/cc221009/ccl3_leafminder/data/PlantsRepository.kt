package com.cc221009.ccl3_leafminder.data

import android.content.Context
import android.util.Log
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.data.model.plantAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun makePlantRepository(context: Context): PlantsRepository {
    return PlantsRepository(
        getDatabase(context).dao,
        plantAPIService,
        "sk-OSgg65a63fbd07e653800"
    )
}

class PlantsRepository(
    val dao: PlantsDao, val apiPlantsService: APIPlantsService,
    val apiKey: String
) {

    suspend fun getAllSpeciesNames(searchFilter: String): List<String> {
        return withContext(Dispatchers.IO) {
            val request = apiPlantsService.getSpeciesList(apiKey, searchFilter)
            val response = request.execute().body()
            val plants = response?.data ?: emptyList()
            plants.map { it.scientific_name.firstOrNull() ?: "Unknown" }
        }
    }

    suspend fun addPlant(plant: Plant) {
        dao.insertPlant(plant)
    }

    suspend fun updatePlant(plant: Plant) {
        dao.updatePlant(plant)
    }

    suspend fun getPlants(): List<Plant> {
        return dao.getPlants()
    }

    suspend fun deletePlant(plant: Plant) {
        dao.deletePlant(plant)
    }

    suspend fun getPlantById(plantId: Int): Plant {
        Log.d("Repository", "Fetching plant with ID: $plantId")
        val plant = dao.getPlantById(plantId)
        Log.d("Repository", "Fetched plant: $plant")
        return plant
    }

    suspend fun upDateWateringDate(plantId: Int, newDate: LocalDate) {
        val plant = getPlantById(plantId)
        Log.d("Repository", "Fetching plant watering date with ID: $plantId")
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = newDate.format(formatter)
        val updatetedPlant = plant.copy(wateringDate = formattedDate)
        Log.d("Repository", "updatet watering date with newDate: $plant")
        updatePlant(updatetedPlant)
    }

}