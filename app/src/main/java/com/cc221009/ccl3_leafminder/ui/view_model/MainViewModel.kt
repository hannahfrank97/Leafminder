package com.cc221009.ccl3_leafminder.ui.view_model

import androidx.lifecycle.ViewModel
import com.cc221009.ccl3_leafminder.ui.view.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun selectScreen(screen: Screen){
       _mainViewState.update { it.copy(selectedScreen = screen) }
    }
}