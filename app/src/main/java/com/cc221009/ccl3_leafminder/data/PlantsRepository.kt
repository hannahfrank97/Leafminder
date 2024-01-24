package com.cc221009.ccl3_leafminder.data

import android.content.Context
import android.util.Log
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.data.model.plantAPIService
import com.cc221009.ccl3_leafminder.ui.view_model.APISpeciesItem
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

private val unknownSpecies = SpeciesDetails(
    id = 0,
    sunlight = listOf("Unknown"),
    watering = "Unknown",
    poisonous_to_humans = 0
)

class PlantsRepository(
    val dao: PlantsDao,
    val apiPlantsService: APIPlantsService,
    val apiKey: String
) {

    suspend fun getAllSpeciesNames(searchFilter: String): List<APISpeciesItem> {
        return withContext(Dispatchers.IO) {
            val request = apiPlantsService.getSpeciesList(apiKey, searchFilter)
            val response = request.execute().body()
            val plants = response?.data ?: emptyList()
            plants
                // NOTE: Holy shit, free plan only allows first 3000 ids for free.
                // So we just filter to only use plants with ids with less than 3000.
                .filter { it.id < 3000 }
                .map {
                    APISpeciesItem(
                        id = it.id,
                        speciesName = it.scientific_name.firstOrNull() ?: "Unknown"
                    )
                }
        }
    }

    suspend fun getSpeciesDetails(Id: Int): SpeciesDetails {
        return withContext(Dispatchers.IO) {
            val request = apiPlantsService.getPlantDetails(Id, apiKey)
            val response = request.execute()
            response.body() ?: unknownSpecies
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