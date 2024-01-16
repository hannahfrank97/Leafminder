package com.cc221009.ccl3_leafminder.data

import android.telecom.Call
import androidx.room.Query
import com.cc221009.ccl3_leafminder.data.model.APIPlants
import retrofit2.http.GET

interface APIPlantsService {
    @GET("APIplants")
    fun listAPIPlants(@Query("apiKey") apiKey:String): Call<List<APIPlants>>
}