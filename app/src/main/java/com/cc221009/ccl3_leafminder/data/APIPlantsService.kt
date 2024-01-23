package com.cc221009.ccl3_leafminder.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class OnlyScientificName(
    val scientific_name: List<String>,
)

data class SpeciesListResponse(
    val data: List<OnlyScientificName>
)


interface APIPlantsService {
    @GET("species-list")
    fun getSpeciesList(
        @Query("key") apiKey: String, @Query("q") query: String
    ): Call<SpeciesListResponse>
}

