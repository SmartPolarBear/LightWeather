package com.smartpolarbear.lightweather.ui.control

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.smartpolarbear.lightweather.R

import com.smartpolarbear.lightweather.ui.animation.ScreenSwitchAnimation
import com.smartpolarbear.lightweather.ui.settings.SettingList
import kotlin.math.exp

@Composable
public fun <T> RawSpinner(
    expanded: Boolean,
    onExpandedChange: ((Boolean) -> Unit)?,
    choices: List<T>,
    onClick: (() -> Unit)?,
    onSelect: ((T) -> Unit)? = {},
    edible: Boolean = false
) {

    var value by remember {
        mutableStateOf(choices.first().toString())
    }

    Box {
        Row {
            Box(modifier = Modifier.clickable {
                if (onClick != null) {
                    onClick()
                }
            })
            {
                Row(
                    modifier = Modifier
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.small_padding)))
                    {
                        if (edible) {
                            TextField(
                                value = value.toString(),
                                onValueChange = { newVal ->
                                    val realChoice = choices.find { item ->
                                        item.toString() == newVal
                                    }
                                    if (realChoice != null) {
                                        value = newVal.toString()
                                        if (onSelect != null) {
                                            onSelect(realChoice)
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Text(text = value)
                        }
                    }

                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Select",
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.small_padding))
                    )
                }
            }

            DropdownMenu(expanded = expanded,
                onDismissRequest = {
                    if (onExpandedChange != null) {
                        onExpandedChange(expanded)
                    }
                }) {
                choices.forEach { choice ->
                    DropdownMenuItem(onClick = {
                        value = choice.toString()
                        if (onSelect != null) {
                            onSelect(choice)
                        }

                        if (onExpandedChange != null) {
                            onExpandedChange(expanded)
                        }
                    }) {
                        Text(
                            text = choice.toString(),
                            modifier = Modifier
                                .wrapContentWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
public fun <T> Spinner(
    choices: List<T>,
    onSelect: ((T) -> Unit)? = {},
    edible: Boolean = false
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.wrapContentSize())
    {
        RawSpinner(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            choices = choices,
            onClick = { expanded = !expanded },
            onSelect = onSelect,
            edible = edible
        )
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Box(modifier = Modifier.wrapContentWidth())
    {
        Spinner(choices = listOf("AAAAA", "BBBBB", "CCCCC"))
    }
}
