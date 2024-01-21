package com.cc221009.ccl3_leafminder.data

import android.util.Log
import com.cc221009.ccl3_leafminder.data.model.Plant

/*class PlantsRepository(private val apiPlantsService: APIPlantsService) {
    suspend fun getPlantsWithDetails(apiKey:String): List<APIPlantsWithDetails> {
        val plantsAPI = apiPlantsService.getlistAPIPlants(apiKey).execute().body() ?: emptyList()
        return withContext(Dispatchers.IO) {
            plantsAPI.map { plant ->
                async {
                    val details = apiPlantsService.getAPIDetails(plant.id, apiKey).execute().body()
                    APIPlantsWithDetails(
                        id = plant.id,
                        scientific_name = plant.scientific_name,
                        watering = plant.watering,
                        sunlight = plant.sunlight,
                        default_image = plant.default_image,
                        poisonous_to_humans = details?.poisonous_to_humans ?: false,
                    )
                }
                }.awaitAll()
            }


        data class APIPlantsWithDetails(
            val id: Int,
            val scientific_name: String,
            val watering: String,
            val sunlight: List<String>,
            val default_image: PlantImage,
            val poisonous_to_humans: Boolean,
        ) */

class PlantsRepository(val dao: PlantsDao) {

    suspend fun getTestAPIDATA(): List<String> {
        return listOf("A", "B", "C")
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

}