package com.cc221009.ccl3_leafminder.data

import android.content.Context
import android.util.Log
import java.io.IOException
import java.util.Properties

object ApiUtils {
    fun getAPIKey(context: Context): String {
        val properties = Properties()
        try {
            val assetManager = context.assets
            val inputStream = assetManager.open("secrets.properties")
            properties.load(inputStream)
        } catch (e: IOException) {
            Log.e("AssetsPropertyReader", e.toString())
        }
        return properties.getProperty("API_KEY", "")
    }
}