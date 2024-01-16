package com.cc221009.ccl3_leafminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.cc221009.ccl3_leafminder.data.PlantsDatabase
import com.cc221009.ccl3_leafminder.ui.theme.CCL3_LeafminderTheme
import com.cc221009.ccl3_leafminder.ui.view.MainView
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModel

class MainActivity : ComponentActivity() {
    private val plantdb by lazy {
        Room.databaseBuilder(this, PlantsDatabase::class.java, "plantDatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val mainViewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(plantdb.dao) as T
                }
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CCL3_LeafminderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(mainViewModel)
                }
            }
        }
    }
}