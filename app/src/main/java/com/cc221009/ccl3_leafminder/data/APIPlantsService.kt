package com.cc221009.ccl3_leafminder.data

import retrofit2.Call
import retrofit2.http.GET
import androidx.room.Query
import com.cc221009.ccl3_leafminder.data.model.APIDetails
import com.cc221009.ccl3_leafminder.data.model.APIPlants
import retrofit2.http.Path

interface APIPlantsService {
    @GET("species-list")
    fun getlistAPIPlants(@Query("apiKey") apiKey: String): Call<List<APIPlants>>

    @GET("species/details/{id}")
    fun getAPIDetails(@Path("id") id: Int, @Query("apiKey") apiKey: String): Call<APIDetails>

}

