package com.cc221009.ccl3_leafminder.data

import com.cc221009.ccl3_leafminder.data.model.PlantImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class PlantsRepository(private val apiPlantsService: APIPlantsService) {
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
class PlantsRepository() {

    suspend fun getTestAPIDATA(): List<String> {
        return listOf("A", "B", "C")

    }
}
            }
        }

    }



        data class APIPlantsWithDetails(
            val id: Int,
            val scientific_name: String,
            val watering: String,
            val sunlight: List<String>,
            val default_image: PlantImage,
            val poisonous_to_humans: Boolean,
        )


