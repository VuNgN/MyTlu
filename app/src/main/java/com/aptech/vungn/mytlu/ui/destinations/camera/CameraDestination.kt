package com.aptech.vungn.mytlu.ui.destinations.camera

import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.aptech.vungn.mytlu.R
import com.aptech.vungn.mytlu.ui.destinations.camera.analyzer.QrCodeAnalyzer
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraDestination(onBack: () -> Unit) {
    var code by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }
    MyTluTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier,
                    title = { Text(text = code ?: "") },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back navigation"
                            )
                        }
                    },
                    actions = {
                        if (cameraPermissionState.status.isGranted) {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Rounded.FlashOn,
                                    contentDescription = "Flash"
                                )
                            }
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Rounded.Cameraswitch,
                                    contentDescription = "Switch camera"
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (cameraPermissionState.status.isGranted) {
                AndroidView(
                    factory = {
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        val preview = Preview.Builder().build()
                            .also { it.setSurfaceProvider(previewView.surfaceProvider) }
                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(Size(previewView.width, previewView.height))
                            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            QrCodeAnalyzer { result ->
                                code = result
                                Log.d("", "CameraDestination: Qr code -> $result")
                            }
                        )
                        try {
                            cameraProviderFuture.get().unbindAll()
                            cameraProviderFuture.get()
                                .bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageAnalysis
                                )

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .clip(RoundedCornerShape(28.dp)),
                )
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (cameraPermissionState.status.shouldShowRationale) {
                            Text(
                                text = stringResource(R.string.should_show_rationale)
                            )
                            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                                Text(text = stringResource(R.string.request_permission))
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.permission_deny)
                            )
                        }
                    }
                }
            }
        }
    }
}