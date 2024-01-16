package com.cc221009.ccl3_leafminder.data.model

 data class APIPlants (
    val id: Int,
    val scientific_name: String,
    val watering: String,
    val sunlight: List<String>,
    val default_image: PlantImage,
    val poisonous_to_humans: Boolean,

)

data class PlantImage(
    val image_id: Int,
    val license: Int,
    val license_name: String,
    val license_url: String,
    val original_url: String,
    val regular_url: String,
    val medium_url: String,
    val small_url: String,
    val thumbnail: String
)