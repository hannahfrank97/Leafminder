package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.ui.view_model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(mainViewModel: MainViewModel, navController: NavController){
    Text(text = "HomeView", fontSize = 50.sp)

}