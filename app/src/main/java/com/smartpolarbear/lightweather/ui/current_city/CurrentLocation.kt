package com.smartpolarbear.lightweather.ui.current_city

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.smartpolarbear.lightweather.settings.DisplayUnit
import com.smartpolarbear.lightweather.weather.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.coroutineContext

@Composable
fun CurrentLocation(navController: NavController) {
    var permissionsGranted by remember {
        mutableStateOf(false)
    }

    var loading by remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
            permissionsGranted = it[Manifest.permission.ACCESS_FINE_LOCATION] ?: false ||
                    it[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        }

    val manager =
        LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val context = LocalContext.current

    if (permissionsGranted) {
        AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {})
    }


    suspend fun loadWeather(): Response<NowWeatherResponse> {
        loading = true
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
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

        val retriever = QWeatherRetriever()
        val response = retriever.getNowWeather(
            location = Location.fromCoordinate(
                location?.longitude ?: 0.0,
                location?.altitude ?: 0.0
            ),
            lang = DisplayLanguages.ZH,
            unit = DisplayUnit.METRIC,
            comp = Compression.GZIP
        )

        loading = false
        return response
    }

    var response by remember { mutableStateOf<Response<NowWeatherResponse>?>(null) }

    LaunchedEffect(Unit)
    {
        response = loadWeather()
    }

    val coroutineScope = rememberCoroutineScope()

    when {
        loading -> CurrentLocationLoadingUI()

        response?.isSuccessful == true -> {
            response?.body()?.let {
                CurrentLocationUI(nowWeatherResponse = it,
                    onRequestReload = {
                        coroutineScope.launch {
                            response = loadWeather()
                        }
                    })

            }
        }

        else -> {
            CurrentLocationErrorUI(code = response?.code().toString(),
                onRequestReload = {
                    coroutineScope.launch {
                        response = loadWeather()
                    }
                })
        }
    }

}

@Composable
fun CurrentLocationUI(
    nowWeatherResponse: NowWeatherResponse,
    onRequestReload: () -> Unit
) {
    Text(text = nowWeatherResponse.now.text)
}

@Composable
fun CurrentLocationErrorUI(
    code: String,
    onRequestReload: () -> Unit
) {
    Text(text = "error $code")
}

@Composable
fun CurrentLocationLoadingUI() {
    Text(text = "loading")
}
