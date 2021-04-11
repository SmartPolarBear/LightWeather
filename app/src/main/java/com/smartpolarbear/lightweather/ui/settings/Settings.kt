package com.smartpolarbear.lightweather.ui.settings

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
import androidx.navigation.NavController
import com.smartpolarbear.lightweather.R

import com.smartpolarbear.lightweather.ui.animation.ScreenSwitchAnimation
import com.smartpolarbear.lightweather.ui.control.RawSpinner
import com.smartpolarbear.lightweather.ui.control.Spinner

/**
 * A switch setting item in the list
 * @param title The Title of the setting item
 * @param description small description under the title
 * @param checked if the switch is checked
 * @param onCheckedChange called when it is toggled
 */
@Composable
fun SwitchSettingItem(
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

@Composable
fun SpinnerBoxSettingItem(
    title: String,
    description: String?
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val toggleExpanded = {
        expanded = !expanded
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.small_padding))
            .clickable { toggleExpanded() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(text = title)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = description ?: "")
            }
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.wrapContentSize()
        )
        {
            RawSpinner(
                expanded = expanded,
                onExpandedChange = { toggleExpanded() },
                choices = listOf("AAA", "BBB", "CCC"),
                onClick = { toggleExpanded() },
                onSelect = { },
                edible = false
            )
        }
    }
}

/**
 * the main setting screen
 * @param navController navigation controller passed by MainActivity
 */
@ExperimentalAnimationApi
@Composable
public fun Settings(navController: NavController) {
    ScreenSwitchAnimation() {
        SettingList()
    }
}

/**
 * The main list of settings
 */
@Composable
fun SettingList() {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            var checked by remember { mutableStateOf(false) }
            SwitchSettingItem("Test", "This is a test", checked) {
                checked = !checked
            }
        }

        item {
            SpinnerBoxSettingItem("Unit", "The unit used to describe weather")
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SettingList()
}