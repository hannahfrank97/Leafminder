package com.cc221009.ccl3_leafminder.data

import android.content.Context
import android.util.Log
import com.cc221009.ccl3_leafminder.data.model.FullPlant
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
        "sk-c0FT65b24e11b18903882"
    )
}

private val unknownSpecies = SpeciesDetails(
    id = 0,
    scientific_name = listOf("Unknown"),
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
//            val request = apiPlantsService.getSpeciesList(apiKey, searchFilter)
//            val response = request.execute()
//            val plants = response.body()?.data ?: emptyList()
            val plants = fakePlantApi.filter { apiPlant ->
                apiPlant.scientific_name.any { name ->
                    name.contains(
                        searchFilter
                    )
                }
            }
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
//            val request = apiPlantsService.getPlantDetails(Id, apiKey)
//            val response = request.execute()
//            response.body() ?: unknownSpecies

            fakePlantApi.getOrNull(Id) ?: unknownSpecies
        }
    }

    suspend fun getSpeciesNameByApiId(apiId: Int): String {
        return withContext(Dispatchers.IO) {
            try {
                // Fetching the list of all species.
                val response = apiPlantsService.getSpeciesList(apiKey, "").execute()

                if (response.isSuccessful && response.body() != null) {
                    // Finding the species that matches the provided apiId.
                    response.body()?.data?.find { it.id == apiId }?.scientific_name?.firstOrNull()
                        ?: "Unknown"
                } else {
                    "Unknown" // Return "Unknown" if the response is unsuccessful or null.
                }
            } catch (e: Exception) {
                Log.e("PlantsRepository", "Error fetching species name: ${e.message}")
                "Unknown"
            }
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

    suspend fun makeFullPlant(plant: Plant): FullPlant {
        if (plant.apiId == null) {
            return FullPlant(
                plant = plant,
                speciesDetails = null
            )
        }

        val speciesDetails = getSpeciesDetails(plant.apiId)
        return FullPlant(
            plant = plant,
            speciesDetails = speciesDetails
        )
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