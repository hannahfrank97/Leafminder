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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditUIState(
    val loadPlant: (Int) -> Unit,
    val plant: Plant? = null,

    val name: TextFieldValue,
    val date: String,
    val size: String,
    val location: String,
    val wellbeing: String,
    val wateringDate: String,
    val wateringFrequency: String,
    val imagePath: String,
    val onNameChange: (TextFieldValue) -> Unit,
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

    val setName: (TextFieldValue) -> Unit,
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
            name = TextFieldValue(""),
            date = "",
            size = "",
            location = "",
            wellbeing = "",
            wateringDate = "",
            wateringFrequency = "",
            imagePath = "",


            onNameChange = {    newName: TextFieldValue -> updateName(newName) },
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

            setName = ::onNameChange,
            setDate = ::onDateChange,
            setLocation = ::onLocationChange,
            setSize = ::onSizeChange,
            setWellbeing = ::onWellbeingChange,
            setWateringFrequency = ::onWateringFrequencyChange,
            setWateringDate = ::onWateringDateChange,

            waterInterval = 20,

            loadPlant = ::getPlantDetails,
            plant = null,

            )
    )

    val uiState: StateFlow<EditUIState> = _mainViewState.asStateFlow()

    fun clickDeletePlant(plant: Plant) {
        viewModelScope.launch {
            plantsRepository.deletePlant(plant)
        }
    }

    fun getPlantDetails(plantId: Int) {
        Log.d("ViewModel", "Loading plant details for ID: $plantId")
        viewModelScope.launch {
            val plantDetails = plantsRepository.getPlantById(plantId)
            Log.d("ViewModel", "Loaded plant details: $plantDetails")
            _mainViewState.value = _mainViewState.value.copy(plant = plantDetails)
        }
    }


    fun showDialog() {
        _mainViewState.update { it.copy(openDialog = true) }
    }

    fun dismissDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun saveEditedPlant(editedPlant: Plant) {
        viewModelScope.launch {
            val existingPlant = uiState.value.plant
            if (existingPlant != null) {
                val updatedPlant = existingPlant.copy(
                    name = if (editedPlant.name.isNotBlank()) editedPlant.name else existingPlant.name,
                    date = if (editedPlant.date.isNotBlank()) editedPlant.date else existingPlant.date,
                    size = if (editedPlant.size.isNotBlank()) editedPlant.size else existingPlant.size,
                    location = if (editedPlant.location.isNotBlank()) editedPlant.location else existingPlant.location,
                    wellbeing = if (editedPlant.wellbeing.isNotBlank()) editedPlant.wellbeing else existingPlant.wellbeing,
                    wateringDate = if (editedPlant.wateringDate.isNotBlank()) editedPlant.wateringDate else existingPlant.wateringDate,
                    wateringFrequency = if (editedPlant.wateringFrequency.isNotBlank()) editedPlant.wateringFrequency else existingPlant.wateringFrequency,
                    imagePath = if (editedPlant.imagePath.isNotBlank()) editedPlant.imagePath else existingPlant.imagePath,

                    id = existingPlant.id
                )

                try {
                    plantsRepository.updatePlant(updatedPlant)
                    Log.d("ViewModel", "Plant updated: $updatedPlant")
                } catch (e: Exception) {
                    Log.e("ViewModel", "Error updating plant: $e")
                }
            } else {
                Log.e("ViewModel", "No existing plant found to update")
            }
        }
    }


    fun updateName(name: TextFieldValue) {
        Log.d("Database", "Updating plant name in database: ")
        _mainViewState.value = _mainViewState.value.copy(name = name)
        Log.d("Database", "Plant name updated in database:")
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

    fun onNameChange(name: TextFieldValue) {
        _mainViewState.value = _mainViewState.value.copy(name = name)
    }

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


    companion object {
        fun provideFactory(plantsRepository: PlantsRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EditPlantViewModel(plantsRepository) as T
                }
            }
        }
    }

}

