//package com.example.detectionpython
//
//import android.app.Activity
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.core.*
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.core.content.ContextCompat
//import com.example.detectionpython.databinding.ActivityCameraBinding
//import java.io.File
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//
//class CameraActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityCameraBinding
//    private lateinit var cameraExecutor: ExecutorService
//    private lateinit var outputDirectory: File
//    private lateinit var imageCapture: ImageCapture
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCameraBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        cameraExecutor = Executors.newSingleThreadExecutor()
//        outputDirectory = getOutputDirectory()
//
//        binding.tooltipText.text = "To capture the best picture, Please position your face within the circle."
//        binding.tooltipText.visibility = View.VISIBLE
//        startCamera()
//
//        binding.capturebtn.setOnClickListener {
//            takePhoto()
//        }
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
//            }
//
//            imageCapture = ImageCapture.Builder().build()
//
//            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
//
//            try {
//                cameraProvider.unbindAll()
//
//                cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview, imageCapture
//                )
//            } catch (exc: Exception) {
//                Log.e("CameraX", "Use case binding failed", exc)
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    private fun takePhoto() {
//        val photoFile = File(outputDirectory, "temp_image.jpg")
//
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    Log.e("CameraX", "Photo capture failed: ${exc.message}", exc)
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val intent = Intent()
//                    intent.putExtra("capturedImagePath", photoFile.absolutePath)
//                    setResult(Activity.RESULT_OK, intent)
//                    finish()
//                }
//            }
//        )
//    }
//
//    private fun getOutputDirectory(): File {
//        return filesDir
//    }
//}