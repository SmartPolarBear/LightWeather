package com.smartpolarbear.lightweather.ui.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartpolarbear.lightweather.ui.theme.LightWeatherTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.smartpolarbear.lightweather.Main
import com.smartpolarbear.lightweather.R

import com.smartpolarbear.lightweather.ui.animation.ScreenSwitchAnimation

@ExperimentalAnimationApi
@Composable
public fun Settings(navController: NavController) {
    ScreenSwitchAnimation() {
        SettingList()
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String?,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(text = title)
            Text(text = description ?: "")
        }
        Box(contentAlignment = Alignment.CenterEnd)
        {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        }
    }
}

@Composable
fun SettingList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingItem("Test", "test", true, {});
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SettingList()
}