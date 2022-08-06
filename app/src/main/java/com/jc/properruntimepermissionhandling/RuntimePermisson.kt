package com.jc.properruntimepermissionhandling

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun requestPermission(){
    var permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START){
                    permissionsState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.addObserver(observer)
            }
        }
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionsState.permissions.forEach { perms ->
            when(perms.permission){
                Manifest.permission.CAMERA -> {
                    when{
                        perms.hasPermission ->{
                            Text(text = "CAMERA permission is granted")
                        }
                        perms.shouldShowRationale ->{
                            Text(text = "Camera persmisson is needed " +
                                    "to access the camera")
                        }
                        perms.isPermanantlyDenied() ->{
                            Text(text = "Camera persmisson is Permanantly Denied" +
                                    "you can enable it into app setting")
                        }
                    }
                }
                Manifest.permission.RECORD_AUDIO -> {
                    when{
                        perms.hasPermission ->{
                            Text(text = "Audio permission is granted")
                        }
                        perms.shouldShowRationale ->{
                            Text(text = "Audio persmisson is needed " +
                                    "to access the microphone")
                        }
                        perms.isPermanantlyDenied() ->{
                            Text(text = "Audio persmisson is Permanantly Denied" +
                                    "you can enable it into app setting")
                        }
                    }
                }
            }
        }

        Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
            Text(text = "Click Here for permission")
        }
    }
}