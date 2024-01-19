package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cc221009.ccl3_leafminder.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {


        delay(2000) // Display splash screen for 2 seconds

        navController.navigate(Screen.HomeView.route) {
            popUpTo(Screen.SplashScreen.route) { inclusive = true } // Clear splash screen from back stack
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.surface) {
        Box(contentAlignment = Alignment.Center) {

            Image(
                painter = painterResource(id = R.drawable.background_loader),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box() {
                Image(
                    painter = painterResource(id = R.drawable.logo_slogan),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    contentScale = ContentScale.Fit
                )
            }
            }
        }
    }
