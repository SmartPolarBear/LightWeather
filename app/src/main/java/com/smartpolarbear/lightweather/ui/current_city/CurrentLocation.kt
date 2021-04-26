package com.smartpolarbear.lightweather.ui.current_city

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.smartpolarbear.lightweather.MainActivity
import com.smartpolarbear.lightweather.MainScreen
import com.smartpolarbear.lightweather.weather.Location
import kotlinx.coroutines.coroutineScope


@Composable
fun CurrentLocation(navController: NavController) {
    val manager = LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { result ->
            AlertDialog(onDismissRequest = { /*TODO*/ },  dismissButton = {})
        }

    if (ContextCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    val fineLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    val coarseLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

    val location = fineLocation ?: coarseLocation // chose the more precious one

}