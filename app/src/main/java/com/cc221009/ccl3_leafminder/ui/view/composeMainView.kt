package com.cc221009.ccl3_leafminder.ui.view

import android.content.Context
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.res.painterResource
import com.cc221009.ccl3_leafminder.R


import java.io.File
import java.util.concurrent.ExecutorService

// https://kotlinlang.org/docs/sealed-classes.html
sealed class Screen(val route: String){
    object HomeView: Screen("homeView")
    object AddView: Screen("addView")
    object DetailView: Screen("detailView")
    object EditView: Screen("editView")
    object PlantListView: Screen("plantListView")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    mainViewModel: MainViewModel
){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {BottomNavigationBar(navController)}
    ) {
        NavHost(
                navController = navController,
                modifier = Modifier.padding(it),
                startDestination = Screen.HomeView.route
            ){
                composable(Screen.HomeView.route){
                    mainViewModel.selectScreen(Screen.HomeView)
                    HomeView(mainViewModel, navController) }

                composable(Screen.AddView.route){
                    mainViewModel.selectScreen(Screen.HomeView)
                    AddView(mainViewModel, navController) }

                composable(Screen.DetailView.route){
                    mainViewModel.selectScreen(Screen.HomeView)
                    DetailView(mainViewModel, navController) }

                composable(Screen.EditView.route){
                    mainViewModel.selectScreen(Screen.HomeView)
                    EditView(mainViewModel, navController) }

                composable(Screen.PlantListView.route){
                    mainViewModel.selectScreen(Screen.HomeView)
                    PlantListView(mainViewModel, navController) }


            }
        }
    }

@Composable
fun BottomNavigationBar(navController: NavHostController){
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(Screen.HomeView.route) },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_home),
                contentDescription = "Delete") })

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AddView.route) },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_add),
                contentDescription = "Delete") })

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