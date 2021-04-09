package com.smartpolarbear.lightweather.ui.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartpolarbear.lightweather.ui.theme.LightWeatherTheme
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.smartpolarbear.lightweather.Main
import com.smartpolarbear.lightweather.R

import com.smartpolarbear.lightweather.ui.animation.ScreenSwitchAnimation

/**
 * A setting item in the list
 * @param title The Title of the setting item
 * @param description small description under the title
 */
@Composable
fun SettingItem(
    title: String,
    description: String?,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier
            .clickable(onClick = {
                if (onCheckedChange != null) {
                    onCheckedChange(checked)
                }
            })
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.small_padding)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(text = title)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = description ?: "")
            }
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

@ExperimentalAnimationApi
@Composable
public fun Settings(navController: NavController) {
    ScreenSwitchAnimation() {
        SettingList()
    }
}


@Composable
fun SettingList() {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            var checked by remember { mutableStateOf(false) }
            SettingItem("Test", "This is a test", checked) {
                checked = !checked
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SettingList()
}