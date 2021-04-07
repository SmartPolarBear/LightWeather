package com.smartpolarbear.lightweather

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
import com.smartpolarbear.lightweather.ui.settings.Settings

class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LightWeatherTheme {
                Main()
            }
        }
    }
}

sealed class MainScreen(val route: String, @StringRes val nameResourceId: Int) {
    object Weather : MainScreen("weather", R.string.app_name)
    object Settings : MainScreen("settings", R.string.settings)
    object About : MainScreen("about", R.string.settings)
}

@ExperimentalAnimationApi
@Composable
fun Main() {
    val navController = rememberNavController();

    Scaffold(topBar = { MainTopBar(navController) },
        floatingActionButton = { MainFloatingActionButton(navController) })
    {
        NavHost(
            navController = navController,
            startDestination = MainScreen.Weather.route
        )
        {
            composable(MainScreen.Weather.route)
            {
            }
            composable(MainScreen.Settings.route)
            {
                Settings(navController)
            }
            composable(MainScreen.About.route)
            {
            }
        }
    }
}

@Composable
fun MainTopBar(navController: NavController) {

    TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            var menuExpanded by remember { mutableStateOf(false) }

            val toggleMenu = {
                menuExpanded = !menuExpanded
            }

            IconButton(onClick = toggleMenu) {
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = toggleMenu
                ) {
                    MainTopBarMenuItems(navController, onItemClick = toggleMenu)
                }

                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.more_action_description)
                )
            }
        })
}

@Composable
fun MainTopBarMenuItems(navController: NavController, onItemClick: () -> Unit) {
    Column() {
        DropdownMenuItem(onClick = {
            onItemClick()

            navController.navigate(MainScreen.Settings.route)
            {
                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }
        }) {
            Text(
                text = stringResource(R.string.settings),
            )
        }
        DropdownMenuItem(onClick = {
            onItemClick()

            navController.navigate(MainScreen.About.route)
            {
                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }
        }) {
            Text(
                text = stringResource(R.string.about),
            )
        }
    }
}

@Composable
fun MainFloatingActionButton(navController: NavController) {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.add_city_description)
            )
            Text(text = stringResource(R.string.add_city))
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LightWeatherTheme {
        Main()
    }
}