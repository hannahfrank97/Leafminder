package com.cc221009.ccl3_leafminder.ui.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.cc221009.ccl3_leafminder.ui.view.AddUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddPlantViewModel : ViewModel() {
    private val _mainViewState = MutableStateFlow(
        AddUIState(
            name = TextFieldValue(""),
            setName = ::onNameChange
        )
    )
    val uiState: StateFlow<AddUIState> = _mainViewState.asStateFlow()

    private fun onNameChange(name: TextFieldValue) {
        _mainViewState.value = _mainViewState.value.copy(name = name)
    }
    /*
    val plantsDetails = MutableLiveData<List<APIPlantsWithDetails>>()

    fun saveButtonPlant(plant: Plants) {
        viewModelScope.launch {
            try {
                dao.insertPlant(plant)
                getPlants()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error saving plant", e)
            }
        }
    }

    fun getPlantsWithDetails(apiKey: String) {
        viewModelScope.launch {
            try {
                val APIdata = repository.getPlantsWithDetails(apiKey)
                plantsDetails.postValue(APIdata)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
*/

}