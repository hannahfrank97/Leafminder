package com.cc221009.ccl3_leafminder.ui.view_model

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.SpeciesDetails
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AddUIState(
    val name: TextFieldValue,
    val setName: (TextFieldValue) -> Unit,
    val setDate: (String) -> Unit,
    val setSize: (String) -> Unit,
    val setLocation: (String) -> Unit,
    val setWellbeing: (String) -> Unit,
    val setWateringDate: (String) -> Unit,
    val setWateringFrequency: (Int) -> Unit,
    val setwaterInterval: (Int) -> Unit,
    val speciesItems: List<APISpeciesItem>,
    val onSpeciesListTapped: () -> Unit,
    val tappingtoSavePlant: (Plant) -> Unit,
    val date: String,
    val size: String,
    val location: String,
    val wellbeing: String,
    val apiId: Int,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,
    val waterInterval: Int,
    val speciesDetails: SpeciesDetails?,
    val onSpeciesSelected: (Int) -> Unit,
)

data class APISpeciesItem(
    val speciesName: String,
    val id: Int,
)

class AddPlantViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

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
            setwaterInterval = ::onWaterIntervalChange,
            speciesItems = emptyList(),
            onSpeciesListTapped = ::fetchSpeciesNames,
            tappingtoSavePlant = ::saveButtonPlant,
            onSpeciesSelected = ::fetchSpeciesDetails,

            date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            size = "",
            location = "",
            wellbeing = "",
            apiId = 0,

            wateringDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),

            wateringFrequency = "",
            imagePath = "",

            waterInterval = 7,

            speciesDetails = null


        )
    )

    val uiState: StateFlow<AddUIState> = _mainViewState.asStateFlow()

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

    fun fetchSpeciesDetails(apiId: Int) {
        viewModelScope.launch {
            try {
                val plantDetails = plantsRepository.getSpeciesDetails(apiId)
                _mainViewState.value = _mainViewState.value.copy(speciesDetails = plantDetails)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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

    fun onWateringFrequencyChange(wateringFrequency: Int) {
        _mainViewState.value = _mainViewState.value.copy(
            wateringFrequency = wateringFrequency.toString(),
            waterInterval = wateringFrequency
        )
    }

    fun onWaterIntervalChange(waterInterval: Int) {
        _mainViewState.value = _mainViewState.value.copy(
            waterInterval = waterInterval,
            wateringFrequency = waterInterval.toString()
        )
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

