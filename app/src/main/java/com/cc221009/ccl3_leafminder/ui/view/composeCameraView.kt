package com.cc221009.ccl3_leafminder.ui.view

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.cc221009.ccl3_leafminder.ui.view_model.CameraViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

@Composable
fun CameraView(
    navController: NavHostController,
    cameraViewModel: CameraViewModel,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    cameraExecutor: ExecutorService,
    directory: File,
    context: Context
) {

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
        AndroidView({previewView}, modifier = Modifier.fillMaxSize())
        Button(
            modifier = Modifier.padding(25.dp),
            onClick = {
                val photoFile = File(
                    directory,
                    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + ".jpg"
                )

                cameraViewModel.takePicture(
                    imageCapture = imageCapture,
                    context = context,
                    onSuccess = { uri ->
                        cameraViewModel.updateCapturedImageUri(uri) // Update the URI of the captured image
                            navController.popBackStack()
                    },
                    onError = { exception ->
                        Log.e("camApp", "Error when capturing image", exception)
                    }
                )
            }
        ) {
            androidx.compose.material3.Icon(
                Icons.Default.AddCircle,
                "Take Photo",
                tint = Color.White
            )
        }
    }
    }
