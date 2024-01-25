package com.cc221009.ccl3_leafminder.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class OnlyScientificName(
    val id: Int,
    val scientific_name: List<String>,
)

data class SpeciesListResponse(
    val data: List<OnlyScientificName>
)

data class SpeciesDetails(
    val id: Int,
    val scientific_name: List<String>,
    val sunlight: List<String>,
    val watering: String,
    /**
     * 0 = non-poisonous, 1 = poisonous
     */
    val poisonous_to_humans: Int,
)


interface APIPlantsService {
    @GET("species-list")
    fun getSpeciesList(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Call<SpeciesListResponse>

    @GET("species/details/{id}")
    fun getPlantDetails(
        @Path("id") Id: Int,
        @Query("key") apiKey: String,
    ): Call<SpeciesDetails>
}

