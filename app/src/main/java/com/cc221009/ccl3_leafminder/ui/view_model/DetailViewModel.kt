package com.cc221009.ccl3_leafminder.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class DetailUIState(
    val loadPlant: (Int) -> Unit,
    val plant: Plant? = null,
    val plants: List<Plant>,
    val updateWateringDate: (Int) -> Unit,


    )

class DetailViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

    private val _mainViewState = MutableStateFlow(
        DetailUIState(
            loadPlant = ::getPlantDetails,
            plant = null,
            plants = emptyList(),
            updateWateringDate = ::updatePlantWateringDate,

            )
    )

    val uiState: StateFlow<DetailUIState> = _mainViewState.asStateFlow()

    init {
        getPlants()
    }

    fun getPlants() {
        viewModelScope.launch {
            try {
                val plants = plantsRepository.getPlants()
                _mainViewState.value = _mainViewState.value.copy(plants = plants)

            } catch (e: Exception) {
                Log.e("PlantListViewModel", "Error saving plant", e)
            }
        }

    }

    fun getPlantDetails(plantId: Int) {
        viewModelScope.launch {
            val plantDetails = plantsRepository.getPlantById(plantId)
            _mainViewState.value = _mainViewState.value.copy(plant = plantDetails)
        }
    }

    fun updatePlantWateringDate(plantId:Int) {
        Log.d("Debug", "Updating watering date for plant ID: $plantId")
        viewModelScope.launch {
            try {
                val currentDate = LocalDate.now()
                plantsRepository.upDateWateringDate(plantId, currentDate)
                getPlants()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error updating watering date", e)
            }
        }
    }


    companion object {
        fun provideFactory(plantsRepository: PlantsRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(plantsRepository) as T
                }
            }
        }
    }

}