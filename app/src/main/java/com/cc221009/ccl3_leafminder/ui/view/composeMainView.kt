package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cc221009.ccl3_leafminder.R

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
                EditView(navController = navController)
            }

            composable(Screen.PlantListView.route) {
                PlantListView(navController = navController)
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
                    contentDescription = "Delete"
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
                    contentDescription = "Delete"
                )
            },
        )


        NavigationBarItem(
            selected = currentRoute == Screen.DetailView.route,
            onClick = { navController.navigate(Screen.DetailView.route) },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            })

        NavigationBarItem(
            selected = currentRoute == Screen.EditView.route,
            onClick = { navController.navigate(Screen.EditView.route) },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = ""
                )
            })

        NavigationBarItem(
            selected = currentRoute == Screen.PlantListView.route,
            onClick = { navController.navigate(Screen.PlantListView.route) },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.List,
                    contentDescription = ""
                )
            })

    }
}