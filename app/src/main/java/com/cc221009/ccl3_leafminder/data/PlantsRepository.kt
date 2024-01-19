package com.cc221009.ccl3_leafminder.data

import com.cc221009.ccl3_leafminder.data.model.Plants

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

    suspend fun addPlant(plant: Plants) {
        dao.insertPlant(plant)
    }

    suspend fun updatePlant(plant: Plants) {
        dao.updatePlant(plant)
    }

    suspend fun getPlants(): List<Plants> {
        return dao.getPlants()
    }

    suspend fun deletePlant(plant: Plants) {
        dao.deletePlant(plant)
    }

    suspend fun getPlantById(plantId: Int): Plants {
        return dao.getPlantById(plantId)
    }
}