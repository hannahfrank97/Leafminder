package com.cc221009.ccl3_leafminder.ui.view

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.cc221009.ccl3_leafminder.data.getDatabase
import com.cc221009.ccl3_leafminder.ui.view_model.AddPlantViewModel
import com.cc221009.ccl3_leafminder.ui.view_model.CameraViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

@Composable
fun CameraView(
    vm: CameraViewModel = viewModel(
        factory = CameraViewModel.provideFactory(
            PlantsRepository(
                getDatabase(LocalContext.current).dao
            )
        )
    ),
    navController: NavHostController,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    cameraExecutor: ExecutorService,
    directory: File,
    context: Context,
) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {

        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(40.dp)
        ){
            // TODO implement back Button
        }
        Button(
            modifier = Modifier.padding(25.dp),
            onClick = {
                val photoFile = File(
                    directory,
                    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + ".jpg"
                )

                // Call takePicture from CameraViewModel
                vm.takePicture(
                    imageCapture = imageCapture,
                    context = context,
                    onSuccess = { uri ->
                        vm.updateCapturedImageUri(uri) // Update the URI of the captured image

                        /*
                        val backScreen = mainViewModel.mainViewState.value.originatingScreenForCamera
                        if (backScreen != null) {
                            navController.navigate(backScreen.route) {
                                popUpTo(backScreen.route) { inclusive = true }
                            }
                        }
                        */
                            navController.navigate(Screen.AddView.route) {
                                popUpTo(Screen.AddView.route) { inclusive = true }

                        }
                    },
                    onError = { exception ->
                        Log.e("camApp", "Error when capturing image", exception)
                    }
                )
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_add), // Use painterResource
                contentDescription = "Take Photo", // Provide content description
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp),
                tint = Color.White
            )
        }
    }
}