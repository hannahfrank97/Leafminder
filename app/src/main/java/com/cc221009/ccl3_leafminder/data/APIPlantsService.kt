package com.cc221009.ccl3_leafminder.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class OnlyScientificName(
    val id: Int,
    val scientific_name: List<String>,
)

data class SpeciesListResponse(
    val data: List<OnlyScientificName>
)

data class SpeciesDetails(
    val sunlight: List<String>,
    val watering: String,
    val poisonous_to_humans: Boolean,
)


interface APIPlantsService {
    @GET("species-list")
    fun getSpeciesList(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Call<SpeciesListResponse>

   @GET("species/details")
   fun getPlantDetails(
       @Query("id") Id: Int,
       @Query("key") apiKey: String,
   ): Call <SpeciesDetails>
}

