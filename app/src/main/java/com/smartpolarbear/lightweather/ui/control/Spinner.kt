package com.smartpolarbear.lightweather.ui.control

import android.widget.Spinner
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.smartpolarbear.lightweather.R

import com.smartpolarbear.lightweather.ui.animation.ScreenSwitchAnimation
import com.smartpolarbear.lightweather.ui.settings.SettingList
import kotlin.math.exp


@Composable
public fun Spinner(
    edible: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row {
            if (edible) {
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = "")
            }

            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = !expanded }) {

            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Box(modifier = Modifier.width(100.dp))
    {
        Spinner()
    }
}
