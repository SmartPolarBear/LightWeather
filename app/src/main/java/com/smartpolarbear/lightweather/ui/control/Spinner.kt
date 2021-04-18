package com.smartpolarbear.lightweather.ui.control

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.smartpolarbear.lightweather.R

@ExperimentalComposeUiApi
@Composable
fun <T> RawSpinner(
    expanded: Boolean,
    onExpandedChange: ((Boolean) -> Unit)?,
    choices: List<T>,
    defaultChoice: T? = null,
    onClick: (() -> Unit)?,
    onSelect: ((T) -> Unit)? = {},
    edible: Boolean = false
) {

    var value by remember {
        mutableStateOf(defaultChoice ?: choices.first())
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
                                        value = realChoice
                                        if (onSelect != null) {
                                            onSelect(realChoice)
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Text(text = value.toString())
                        }
                    }

                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Select",
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.small_padding))
                    )
                }


                DropdownMenu(
                    expanded = expanded,
                    offset = DpOffset(0.dp, LocalView.current.measuredHeight.dp),
                    onDismissRequest = {
                        if (onExpandedChange != null) {
                            onExpandedChange(expanded)
                        }
                    },
                ) {
                    choices.forEach { choice ->
                        DropdownMenuItem(onClick = {
                            value = choice
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
}

@ExperimentalComposeUiApi
@Composable
fun <T> Spinner(
    choices: List<T>,
    onSelect: ((T) -> Unit)? = {},
    defaultChoice: T? = null,
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
            defaultChoice = defaultChoice,
            onClick = { expanded = !expanded },
            onSelect = onSelect,
            edible = edible
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Box(modifier = Modifier.wrapContentWidth())
    {
        val items = listOf("AAAAA", "BBBBB", "CCCCC")
        Spinner(choices = items, defaultChoice = items[2])
    }
}
