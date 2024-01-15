package com.cc221009.ccl3_leafminder

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.cc221009.ccl3_leafminder.data.PlantsDatabase
import com.cc221009.ccl3_leafminder.ui.theme.CCL3_LeafminderTheme


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