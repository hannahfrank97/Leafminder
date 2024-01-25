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

data class PlantListUIState(
    val plants: List<Plant>,
    val seeAllPlants: () -> Unit,
    val speciesItems: List<APISpeciesItem>,
    val onSpeciesListRequested: () -> Unit,
    val getSpeciesNameById: (Int) -> Unit,
    val fetchedSpeciesName: String,

    )

class PlantListViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {
    private val _mainViewState = MutableStateFlow(
        PlantListUIState(
            plants = emptyList(),
            seeAllPlants = ::getPlants,
            speciesItems = emptyList(),
            onSpeciesListRequested = ::fetchSpeciesNames,
            getSpeciesNameById = ::fetchSpeciesNameById,
            fetchedSpeciesName = ""
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
                _mainViewState.value = _mainViewState.value.copy(plants = plants)
            } catch (e: Exception) {
                Log.e("PlantListViewModel", "Error saving plant", e)
            }
        }


    }

    fun fetchSpeciesNames() {
        viewModelScope.launch {
            try {
                val speciesItems = plantsRepository.getAllSpeciesNames("rubrum")
                _mainViewState.value = uiState.value.copy(speciesItems = speciesItems)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    fun fetchSpeciesNameById(apiId: Int) {
        viewModelScope.launch {
            try {
                val speciesName = plantsRepository.getSpeciesNameByApiId(apiId)
                _mainViewState.value = _mainViewState.value.copy(fetchedSpeciesName = speciesName)
            } catch (e: Exception) {
                e.printStackTrace()
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