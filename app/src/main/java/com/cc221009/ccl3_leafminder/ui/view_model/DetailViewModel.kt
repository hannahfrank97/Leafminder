package com.cc221009.ccl3_leafminder.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.FullPlant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUIState(
    val loadPlant: (Int) -> Unit,
    val fullPlant: FullPlant?,
)

class DetailViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

    private val _mainViewState = MutableStateFlow(
        DetailUIState(
            loadPlant = ::getPlantDetails,
            fullPlant = null
        )
    )

    val uiState: StateFlow<DetailUIState> = _mainViewState.asStateFlow()


    fun getPlantDetails(plantId: Int) {
        viewModelScope.launch {
            val plant = plantsRepository.getPlantById(plantId)
            val fullPlant = plantsRepository.makeFullPlant(plant)
            _mainViewState.value =
                _mainViewState.value.copy(fullPlant = fullPlant)
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