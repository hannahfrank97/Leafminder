package com.cc221009.ccl3_leafminder.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditUIState(
    val name: String,
    val date: String,
    val size: String,
    val location: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,
    val onNameChange: (String) -> Unit,
    val onDateChange: (String) -> Unit,
    val onSizeChange: (String) -> Unit,
    val onLocationChange: (String) -> Unit,
    val onWellbeingChange: (String) -> Unit,
    val onWateringDateChange: (String) -> Unit,
    val onWateringFrequencyChange: (String) -> Unit,
    val onImagePathChange: (String) -> Unit,

    val onSaveEditedPlant: (Plant) -> Unit,

    val openDialog: Boolean,

    val clickDismissDialog: () -> Unit,

    val clickShowDialog: () -> Unit,

    val clickingToDeletePlant: (Plant) -> Unit,

    val setDate: (String) -> Unit,
    val setSize: (String) -> Unit,
    val setWellbeing: (String) -> Unit,
    val setLocation: (String) -> Unit,
    val setWateringFrequency: (Int) -> Unit,
    val setWateringDate: (String) -> Unit,
    val waterInterval: Int,
)

class EditPlantViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

    private val _mainViewState = MutableStateFlow(
        EditUIState(
            name = "",
            date = "",
            size = "",
            location = "",
            wellbeing = "",
            wateringDate = "",
            wateringFrequency = "",
            imagePath = "",
            onNameChange = { newName: String -> updateName(newName) },
            onDateChange = { newDate: String -> updateDate(newDate) },
            onSizeChange = { newSize: String -> updateSize(newSize) },
            onWellbeingChange = { newWellbeing: String -> updateWellbeing(newWellbeing) },
            onLocationChange = { newLocation: String -> updateLocation(newLocation) },
            onWateringDateChange = { newWateringDate: String -> updateWateringDate(newWateringDate) },
            onWateringFrequencyChange = { newWateringFrequency: String ->
                updateWateringFrequency(
                    newWateringFrequency
                )
            },
            onImagePathChange = { newImagePath: String -> updateImagePath(newImagePath) },
            onSaveEditedPlant = ::saveEditedPlant,
            openDialog = false,
            clickDismissDialog = ::dismissDialog,
            clickShowDialog = ::showDialog,
            clickingToDeletePlant = ::clickDeletePlant,

            setDate = ::onDateChange,
            setLocation = ::onLocationChange,
            setSize = ::onSizeChange,
            setWellbeing = ::onWellbeingChange,
            setWateringFrequency = ::onWateringFrequencyChange,
            setWateringDate = ::onWateringDateChange,

            waterInterval = 20,

            )
    )

    val uiState: StateFlow<EditUIState> = _mainViewState.asStateFlow()

    fun clickDeletePlant(plant: Plant) {
        viewModelScope.launch {
            plantsRepository.deletePlant(plant)
        }
    }

    fun showDialog() {
        _mainViewState.update { it.copy(openDialog = true) }
    }

    fun dismissDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun saveEditedPlant(plant: Plant) {
        viewModelScope.launch {
            plantsRepository.updatePlant(plant)
        }
    }

    fun updateName(name: String) {
        _mainViewState.value = _mainViewState.value.copy(name = name)
    }

    fun updateDate(date: String) {
        _mainViewState.value = _mainViewState.value.copy(date = date)
    }

    fun updateSize(size: String) {
        _mainViewState.value = _mainViewState.value.copy(size = size)
    }

    fun updateLocation(location: String) {
        _mainViewState.value = _mainViewState.value.copy(location = location)
    }

    fun updateWellbeing(wellbeing: String) {
        _mainViewState.value = _mainViewState.value.copy(wellbeing = wellbeing)
    }

    fun updateWateringDate(wateringDate: String) {
        _mainViewState.value = _mainViewState.value.copy(wateringDate = wateringDate)
    }

    fun updateWateringFrequency(wateringFrequency: String) {
        _mainViewState.value = _mainViewState.value.copy(wateringFrequency = wateringFrequency)
    }

    fun updateImagePath(imagePath: String) {
        _mainViewState.value = _mainViewState.value.copy(imagePath = imagePath)
    }

    /*fun onNameChange(name: TextFieldValue) {
        _mainViewState.value = _mainViewState.value.copy(name = name)
    }*/

    fun onDateChange(date: String) {
        _mainViewState.value = _mainViewState.value.copy(date = date)
    }

    fun onSizeChange(size: String) {
        _mainViewState.value = _mainViewState.value.copy(size = size)
    }

    fun onWellbeingChange(wellbeing: String) {
        _mainViewState.value = _mainViewState.value.copy(wellbeing = wellbeing)
    }

    fun onLocationChange(location: String) {
        _mainViewState.value = _mainViewState.value.copy(location = location)
    }

    fun onWateringFrequencyChange(wateringFrequency: Int) {
        _mainViewState.value = _mainViewState.value.copy(
            wateringFrequency = wateringFrequency.toString(),
            waterInterval = wateringFrequency
        )
    }

    fun onWateringDateChange(wateringDate: String) {
        _mainViewState.value = _mainViewState.value.copy(wateringDate = wateringDate)
    }

    fun onWaterIntervalChange(waterInterval: Int) {
        _mainViewState.value = _mainViewState.value.copy(
            waterInterval = waterInterval,
            wateringFrequency = waterInterval.toString()
        )
    }


    companion object {
        fun provideFactory(
            plantsRepository: PlantsRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EditPlantViewModel(plantsRepository) as T
            }
        }
    }


}

