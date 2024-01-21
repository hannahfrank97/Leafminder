package com.cc221009.ccl3_leafminder.ui.view

import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.ui.view_model.CameraViewModel
import java.io.File
import java.util.concurrent.ExecutorService

// https://kotlinlang.org/docs/sealed-classes.html
sealed class Screen(val route: String) {
    object HomeView : Screen("homeView")
    object AddView : Screen("addView")
    object DetailView : Screen("detailView")
    object EditView : Screen("editView")
    object PlantListView : Screen("plantListView")
    object SplashScreen : Screen("splashScreen")
    object CameraView : Screen("cameraView")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    cameraViewModel: CameraViewModel,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    cameraExecutor: ExecutorService,
    directory: File
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route !== Screen.SplashScreen.route) {
                BottomNavigationBar(navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.SplashScreen.route
        ) {
            composable(Screen.HomeView.route) {
                HomeView(navController)
            }

            composable(Screen.AddView.route) {
                AddView(navController = navController)
            }

            composable(
                "DetailView/{plantId}",
                arguments = listOf(navArgument("plantId") { type = NavType.IntType })
            ) { backStackEntry ->
                val plantId = backStackEntry.arguments?.getInt("plantId")!!
                DetailView(plantId, navController = navController)
            }

            composable(Screen.EditView.route) {
                EditView(navController = navController)
            }

            composable(Screen.PlantListView.route) {
                PlantListView(navController = navController)
            }

            composable(Screen.SplashScreen.route) {
                SplashScreen(navController = navController)
            }

            composable(Screen.CameraView.route) {
                CameraView(
                    navController,
                    cameraViewModel,
                    previewView,
                    imageCapture,
                    cameraExecutor,
                    directory)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.HomeView.route,
            onClick = { navController.navigate(Screen.HomeView.route) },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.icon_home),
                    contentDescription = "home icon"
                )
            }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.AddView.route,
            onClick = { navController.navigate(Screen.AddView.route) },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "Add icon"
                )
            },
        )

        NavigationBarItem(
            selected = currentRoute == Screen.PlantListView.route,
            onClick = { navController.navigate(Screen.PlantListView.route) },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.icon_list),
                    contentDescription = "plant list"
                )
            })

    }
}