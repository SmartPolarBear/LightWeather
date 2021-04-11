package com.smartpolarbear.lightweather.ui.control

import android.widget.Spinner
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
public fun Spinner(
    choices: List<String>,
    edible: Boolean = false
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var value by remember {
        mutableStateOf(choices.first())
    }

    Box {
        Row {
            Box(modifier = Modifier.clickable { expanded = !expanded })
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
                                value = value,
                                onValueChange = { newVal ->
                                    if (choices.contains(newVal)) {
                                        value = newVal
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
                onDismissRequest = { expanded = !expanded }) {
                choices.forEach { choice ->
                    DropdownMenuItem(onClick = {
                        value = choice
                        expanded = !expanded
                    }) {
                        Text(
                            text = choice,
                            modifier = Modifier
                                .wrapContentWidth()
                        )
                    }
                }
            }
        }
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
