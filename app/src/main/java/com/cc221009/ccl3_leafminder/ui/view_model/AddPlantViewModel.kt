package com.cc221009.ccl3_leafminder.ui.view_model

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import com.cc221009.ccl3_leafminder.ui.view.AddUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddPlantViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

    //for the api: val plantsDetails = MutableLiveData<List<APIPlantsWithDetails>>()
    private val _mainViewState = MutableStateFlow(
        AddUIState(
            name = TextFieldValue(""),
            setName = ::onNameChange,

            speciesNames = emptyList(),
            onSpeciesListTapped = ::fetchSpeciesNames,
            tappingtoSavePlant = ::saveButtonPlant,

            date = "",
            size = "",
            wellbeing = "",
            wateringDate = "",
            wateringFrequency = "",
            imagePath = "",

            )
    )
    val uiState: StateFlow<AddUIState> = _mainViewState.asStateFlow()

    fun onNameChange(name: TextFieldValue) {
        _mainViewState.value = _mainViewState.value.copy(name = name)
    }

    fun fetchSpeciesNames() {
        viewModelScope.launch {
            try {
                val speciesNames = plantsRepository.getTestAPIDATA()
                _mainViewState.value = _mainViewState.value.copy(speciesNames = speciesNames)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    fun saveButtonPlant(plant: Plant) {
        viewModelScope.launch {
            try {
                plantsRepository.addPlant(plant)
                //getPlants()
            } catch (e: Exception) {
                Log.e("AddPlantViewModel", "Error saving plant", e)
            }
        }
    }


    /*suspend fun getPlants() {
        viewModelScope.launch {
            dao.getPlants().collect { plants ->
                //_mainViewState.update { it.copy(name = plants.) }
            }
        }


    }*/

    //API functions (unsure id this is needed):
    /*class APIViewModel (private val repository: PlantsRepository,apiKey: String): ViewModel() {
    val plantsDetails = MutableLiveData<List<APIPlantsWithDetails>>()



    init {
        viewModelScope.launch {
            try {
                val APIdata = repository.getPlantsWithDetails(apiKey)
                plantsDetails.postValue(APIdata)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}*/

    //CREATING A FACTORY FOR THE ADDPLANTSVIEWMODEL
    companion object {
        fun provideFactory(plantsRepository: PlantsRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AddPlantViewModel(plantsRepository) as T
                }
            }
        }
    }

}