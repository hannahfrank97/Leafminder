package com.cc221009.ccl3_leafminder.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc221009.ccl3_leafminder.data.PlantsDao
import com.cc221009.ccl3_leafminder.data.model.Plants
import com.cc221009.ccl3_leafminder.ui.view.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val  mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun selectScreen(screen: Screen){
       _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    /*suspend fun getPlants() {
        viewModelScope.launch {
            dao.getPlants().collect { plants ->
                _mainViewState.update { it.copy(plants =  plants) }
            }
        }
    }*/


    /*fun editPlant(plant: Plants) {
        _mainViewState.update { it.copy(editPlant = plant) }
    }*/

    fun saveEditedPlant(plant: Plants) {
        viewModelScope.launch {
            try {
              //  dao.updatePlant(plant)

            } catch (e: Exception) {
                Log.e("MainViewModel", "Error saving plant", e)
            }
        }
    }

    fun clickdeletePlant(plant: Plants) {
        viewModelScope.launch {
          //  dao.deletePlant(plant)

        }
    }

}

