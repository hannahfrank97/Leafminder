package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List

// https://kotlinlang.org/docs/sealed-classes.html
sealed class Screen(val route: String) {
    object HomeView : Screen("homeView")
    object AddView : Screen("addView")
    object DetailView : Screen("detailView")
    object EditView : Screen("editView")
    object PlantListView : Screen("plantListView")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.HomeView.route
        ) {
            composable(Screen.HomeView.route) {
                HomeView(navController)
            }

            composable(Screen.AddView.route) {
                AddView(navController = navController)
            }

            composable(Screen.DetailView.route) {
                DetailView(navController)
            }

            composable(Screen.EditView.route) {
                EditView(navController=navController)
            }

            composable(Screen.PlantListView.route) {
                PlantListView(navController)
            }


        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(Screen.HomeView.route) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "") })

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AddView.route) },
            icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "") })

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.DetailView.route) },
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "") })

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.EditView.route) },
            icon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "") })

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.PlantListView.route) },
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "") })

    }
}