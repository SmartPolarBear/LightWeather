package com.smartpolarbear.lightweather.ui.settings

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.smartpolarbear.lightweather.R
import com.smartpolarbear.lightweather.settings.DisplayUnit

import com.smartpolarbear.lightweather.ui.animation.ScreenSwitchAnimation
import com.smartpolarbear.lightweather.ui.control.RawSpinner
import java.security.InvalidParameterException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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

/**
 * A setting item which has many choices to select from
 * @param title The Title of the setting item
 * @param description small description under the title
 * @param choices choices to select from
 * @param defaultChoice the default choice to be displayed at first
 * @param onSelected called when select item changes
 */
@Composable
fun <T> SpinnerBoxSettingItem(
    title: String,
    description: String?,
    choices: List<T>,
    defaultChoice: T? = null,
    onSelected: ((T) -> Unit)?
) {
    if (defaultChoice != null && !choices.contains(defaultChoice)) {
        throw InvalidParameterException()
    }

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
                choices = choices,
                defaultChoice = defaultChoice,
                onClick = { toggleExpanded() },
                onSelect = onSelected,
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
            class UnitChoice(val displayName: String, val theValue: DisplayUnit) {
                override fun toString(): String {
                    return displayName
                }

                val value: DisplayUnit
                    get() {
                        return theValue
                    }
            }

            val context = LocalContext.current

            SpinnerBoxSettingItem(title = "Unit",
                description = "The unit used to describe weather",
                choices = listOf(
                    stringResource(id = DisplayUnit.METRIC.displayNameResId),
                    stringResource(id = DisplayUnit.IMPERIAL.displayNameResId),
                ),
                defaultChoice = stringResource(id = DisplayUnit.get(LocalContext.current).displayNameResId),
                onSelected = { theUnit ->
                    when (theUnit) {
                        context.getString(DisplayUnit.METRIC.displayNameResId) -> DisplayUnit.METRIC.save(
                            context
                        )

                        context.getString(DisplayUnit.IMPERIAL.displayNameResId) -> DisplayUnit.IMPERIAL.save(
                            context
                        )

                        else -> throw InvalidParameterException()
                    }
                })
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SettingList()
}