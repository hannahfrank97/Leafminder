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
import java.time.format.DateTimeFormatter


data class HomeUIState (
    val plants: List<Plant>,
    val plant: Plant? = null,
    val seeAllPlants: () -> Unit,
    val updateWateringDate: (Int) -> Unit,

    )
class HomeViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {
    private val _mainViewState = MutableStateFlow(
        HomeUIState(
            plants = emptyList(),
            seeAllPlants = ::getPlants,
            updateWateringDate = ::updatePlantWateringDate,
            plant = null,

        )
    )

    val uiState: StateFlow<HomeUIState> = _mainViewState.asStateFlow()

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
                    return HomeViewModel(plantsRepository) as T
                }
            }
        }
    }



}