package com.cc221009.ccl3_leafminder.ui.view_model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors

data class CameraState (
    val photosListState: List<Uri> = emptyList(),
    val enableCameraPreview: Boolean = false,
    val cameraPermissionGranted: Boolean = false,
)

class CameraViewModel() : ViewModel() {
    private val _cameraState = MutableStateFlow(CameraState())
    val uiState: StateFlow<CameraState> = _cameraState.asStateFlow()

    private var _capturedImageUri = mutableStateOf<Uri?>(null)
    var capturedImageUri: State<Uri?> = _capturedImageUri

    fun updateCapturedImageUri(uri: Uri?) {
        _capturedImageUri.value = uri
        Log.e("_capturedImageUri", _capturedImageUri.toString())
        Log.e("capturedImageUri", capturedImageUri.toString())
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
        val directory = context.filesDir.resolve("plantimages").apply { mkdirs() }

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
                        updateCapturedImageUri(uri)
                        onSuccess(uri)
                    }
                }
            }
        )
    }

    // –––––––––– COMPANION

    companion object {
        fun provideFactory(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CameraViewModel() as T
                }
            }
        }
    }

}