package com.cc221009.ccl3_leafminder.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.cc221009.ccl3_leafminder.data.model.APIDetails
import com.cc221009.ccl3_leafminder.data.model.APIPlants
import retrofit2.http.Header

interface APIPlantsService {
    @GET("species-list")
    fun getlistAPIPlants(@Header("apiKey") apiKey: String): Call<List<APIPlants>>

    @GET("species/details/{id}")
    fun getAPIDetails(@Header("id") id: Int, @Query("apiKey") apiKey: String): Call<APIDetails>

}

