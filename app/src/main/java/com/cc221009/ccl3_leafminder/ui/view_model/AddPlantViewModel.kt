package com.cc221009.ccl3_leafminder.ui.view_model

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddUIState(
    val name: TextFieldValue,
    val setName: (TextFieldValue) -> Unit,
    val setDate: (String) -> Unit,
    val setSize: (String) -> Unit,
    val setLocation: (String) -> Unit,
    val setWellbeing: (String) -> Unit,
    val setWateringDate: (String) -> Unit,
    val setWateringFrequency: (String) -> Unit,
    val speciesNames: List<String>,
    val onSpeciesListTapped: () -> Unit,
    val tappingtoSavePlant: (Plant) -> Unit,
    val date: String,
    val size: String,
    val location: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,

)

class AddPlantViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

    //for the api: val plantsDetails = MutableLiveData<List<APIPlantsWithDetails>>()
    private val _mainViewState = MutableStateFlow(
        AddUIState(
            name = TextFieldValue(""),
            setName = ::onNameChange,
            setDate = ::onDateChange,
            setLocation = ::onLocationChange,
            setSize = ::onSizeChange,
            setWellbeing = ::onWellbeingChange,
            setWateringDate = ::onWateringDateChange,
            setWateringFrequency = ::onWateringFrequencyChange,

            speciesNames = emptyList(),
            onSpeciesListTapped = ::fetchSpeciesNames,
            tappingtoSavePlant = ::saveButtonPlant,

            date = "",
            size = "",
            location = "",
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

    fun onDateChange(date: String) {
        _mainViewState.value = _mainViewState.value.copy(date = date)
    }

    fun onLocationChange(location: String) {
        _mainViewState.value = _mainViewState.value.copy(location = location)
    }

    fun onSizeChange(size: String) {
        _mainViewState.value = _mainViewState.value.copy(size = size)
    }

    fun onWellbeingChange(wellbeing: String) {
        _mainViewState.value = _mainViewState.value.copy(wellbeing = wellbeing)
    }

    fun onWateringDateChange(wateringDate: String) {
        _mainViewState.value = _mainViewState.value.copy(wateringDate = wateringDate)
    }

    fun onWateringFrequencyChange(wateringFrequency: String) {
        _mainViewState.value = _mainViewState.value.copy(wateringFrequency = wateringFrequency)
    }

    //TODO: add image path change (dont know the functionality of image upload so dont know if this is needed)

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

