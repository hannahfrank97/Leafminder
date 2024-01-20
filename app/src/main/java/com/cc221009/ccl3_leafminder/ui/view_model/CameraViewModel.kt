package com.cc221009.ccl3_leafminder.ui.view_model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cc221009.ccl3_leafminder.data.PlantsRepository
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

data class CameraState (
    val photosListState: List<Uri> = emptyList(),
    val enableCameraPreview: Boolean = false,
    val cameraPermissionGranted: Boolean = false
)

class CameraViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {
    private val _cameraState = MutableStateFlow(CameraState())



    private var _capturedImageUri = mutableStateOf<Uri?>(null)
    var capturedImageUri: State<Uri?> = _capturedImageUri

    fun updateCapturedImageUri(uri: Uri?) {
        _capturedImageUri.value = uri
    }

    fun setCameraPermission(value: Boolean){
        _cameraState.update { it.copy(cameraPermissionGranted = value) }
    }

    fun enableCameraPreview(value: Boolean){
        _cameraState.update { it.copy(enableCameraPreview = value) }
    }

    fun setNewUri(value: Uri){
        _cameraState.update { it.copy(photosListState = it.photosListState + value) }
        enableCameraPreview(false)
    }

    fun takePicture(imageCapture: ImageCapture, context: Context, onSuccess: (Uri) -> Unit, onError: (ImageCaptureException) -> Unit) {
        // Get or create the directory where images will be stored
        val directory = context.filesDir.resolve("bookImages").apply { mkdirs() }

        // Create a file to save the image
        val photoFile = File(directory, SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has been taken
        imageCapture.takePicture(
            outputOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    // Handle error on main thread
                    CoroutineScope(Dispatchers.Main).launch {
                        onError(exc)
                    }
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // Update the UI on the main thread
                    CoroutineScope(Dispatchers.Main).launch {
                        val uri = Uri.fromFile(photoFile)
                        onSuccess(uri)
                        updateCapturedImageUri(uri) // Update capturedImageUri on main thread
                    }
                }
            }
        )
    }

    // –––––––––– COMPANION

    companion object {
        fun provideFactory(plantsRepository: PlantsRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CameraViewModel(plantsRepository) as T
                }
            }
        }
    }

}