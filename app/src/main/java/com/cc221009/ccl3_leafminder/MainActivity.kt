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
import com.cc221009.ccl3_leafminder.data.PlantsDatabase
import com.cc221009.ccl3_leafminder.ui.theme.CCL3_LeafminderTheme
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModelPlants

class MainActivity : ComponentActivity() {
    private val plantdb by lazy {
        Room.databaseBuilder(this, PlantsDatabase::class.java, "plantDatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val mainViewModelDiary by viewModels<MainViewModelPlants>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModelPlants(plantdb.dao) as T
                }

            }
        })

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CCL3_LeafminderTheme {
        Greeting("Android")
    }
}