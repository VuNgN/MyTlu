package com.aptech.vungn.mytlu.ui.destinations.camera

import android.Manifest
import android.content.Context
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.aptech.vungn.mytlu.R
import com.aptech.vungn.mytlu.ui.destinations.camera.analyzer.QrCodeAnalyzer
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.common.util.concurrent.ListenableFuture

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun CameraDestination(onBack: () -> Unit) {
    var code by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Camera
    val cameraSelector = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState =
        rememberPermissionState(permission = Manifest.permission.CAMERA)
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    // Bottom sheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = skipHalfExpanded)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }
    LaunchedEffect(key1 = cameraSelector.value, key2 = openBottomSheet) {
        if (!openBottomSheet) {
            startCamera(
                previewView,
                context,
                cameraProviderFuture,
                lifecycleOwner,
                cameraSelector,
                onScanned(
                    updateCode = { code = it },
                    openBottomSheet = { openBottomSheet = !openBottomSheet },
                    unbindCamera = { cameraProviderFuture.get().unbindAll() }
                )
            )
        }
    }
    MyTluTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier,
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = stringResource(R.string.description_back_navigation)
                            )
                        }
                    },
                    actions = {
                        if (cameraPermissionState.status.isGranted) {
                            IconButton(onClick = {
                                cameraSelector.value = when (cameraSelector.value) {
                                    CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
                                    CameraSelector.DEFAULT_FRONT_CAMERA -> CameraSelector.DEFAULT_BACK_CAMERA
                                    else -> CameraSelector.DEFAULT_BACK_CAMERA
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Cameraswitch,
                                    contentDescription = stringResource(R.string.description_switch_camera)
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
    if (openBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.offset(0.dp, 20.dp),
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = code
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
                    Icon(imageVector = Icons.Rounded.CheckCircle, contentDescription = "")
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.button_attendance))
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

private fun onScanned(
    updateCode: (String) -> Unit,
    openBottomSheet: () -> Unit,
    unbindCamera: () -> Unit
): (String) -> Unit {
    return { result ->
        updateCode(result)
        unbindCamera()
        openBottomSheet()
    }
}

private fun startCamera(
    previewView: PreviewView,
    context: Context,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: MutableState<CameraSelector>,
    onScanning: (String) -> Unit
) {
    val preview = Preview.Builder().build()
        .also { it.setSurfaceProvider(previewView.surfaceProvider) }
    val imageAnalysis = ImageAnalysis.Builder()
        .setTargetResolution(Size(previewView.width, previewView.height))
        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
        .build()
    imageAnalysis.setAnalyzer(
        ContextCompat.getMainExecutor(context),
        QrCodeAnalyzer(qrCodeScanned = onScanning)
    )
    try {
        cameraProviderFuture.get().unbindAll()
        cameraProviderFuture.get()
            .bindToLifecycle(
                lifecycleOwner,
                cameraSelector.value,
                preview,
                imageAnalysis
            )

    } catch (e: Exception) {
        e.printStackTrace()
    }
}