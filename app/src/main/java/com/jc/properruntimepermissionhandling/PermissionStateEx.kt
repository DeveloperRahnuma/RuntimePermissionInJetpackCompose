package com.jc.properruntimepermissionhandling

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanantlyDenied() : Boolean{
return !hasPermission && !shouldShowRationale
}