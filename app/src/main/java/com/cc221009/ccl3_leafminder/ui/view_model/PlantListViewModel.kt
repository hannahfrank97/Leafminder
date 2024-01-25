package com.cc221009.ccl3_leafminder.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.FullPlant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlantListUIState(
    val fullPlants: List<FullPlant>,
    val seeAllPlants: () -> Unit,
)

class PlantListViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {
    private val _mainViewState = MutableStateFlow(
        PlantListUIState(
            fullPlants = emptyList(),
            seeAllPlants = ::getPlants,
        )

    )


    val uiState: StateFlow<PlantListUIState> = _mainViewState.asStateFlow()

    init {
        getPlants()
    }

    fun getPlants() {
        viewModelScope.launch {
            try {
                val plants = plantsRepository.getPlants()

                // Create a new list for fullPlants
                val fullPlantsList = mutableListOf<FullPlant>()

                for (plant in plants) {
                    val fullPlant = plantsRepository.makeFullPlant(plant)
                    fullPlantsList.add(fullPlant)
                }

                // Update the state with the new list
                _mainViewState.value = _mainViewState.value.copy(
                    fullPlants = fullPlantsList
                )
            } catch (e: Exception) {
                Log.e("PlantListViewModel", "Error fetching plants", e)
            }
        }
    }

    companion object {
        fun provideFactory(plantsRepository: PlantsRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlantListViewModel(plantsRepository) as T
                }
            }
        }
    }
}