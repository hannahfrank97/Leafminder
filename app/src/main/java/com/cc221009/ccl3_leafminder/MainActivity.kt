package com.cc221009.ccl3_leafminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.cc221009.ccl3_leafminder.ui.theme.CCL3_LeafminderTheme
import com.cc221009.ccl3_leafminder.ui.view.MainView
import com.cc221009.ccl3_leafminder.ui.view_model.CameraViewModel
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private val cameraViewModel = CameraViewModel()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        cameraViewModel.setCameraPermission(it)
    }

    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    private val imageCapture : ImageCapture = ImageCapture.Builder().build()
    private val preview : Preview = Preview.Builder().build()

    private fun setupCamera(){
        previewView = PreviewView(this)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun bindPreview(cameraProvider : ProcessCameraProvider) {
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build(),
            preview, imageCapture)

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       requestPermission()
       setupCamera()

        setContent {
            CCL3_LeafminderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val camState = cameraViewModel.uiState.collectAsState()

                    MainView(
                        cameraViewModel,
                        previewView,
                        imageCapture,
                        cameraExecutor,
                        getOutputDirectory()
                    )
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun requestPermission(){
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA).let{ result ->
            if(result != PackageManager.PERMISSION_GRANTED){
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            } else {
                cameraViewModel.setCameraPermission(true)
            }
        }
    }

    /*
    // CAMERA IMPLEMENTATION


    private fun setupCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        imageCapture = ImageCapture.Builder().build()
        previewView = PreviewView(this)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    private fun requestCameraPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) -> {
                cameraViewModel.setCameraPermission(true)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun requestPermission(){
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA).let{ result ->
            if(result != PackageManager.PERMISSION_GRANTED){
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            } else {
                cameraViewModel.setCameraPermission(true)
            }
        }
    } */
}