package com.smartpolarbear.lightweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
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

sealed class MainScreen(
    val route: String,
    @StringRes val nameResourceId: Int,
    val imageVector: ImageVector
) {
    object AllWeathers : MainScreen("all_weather", R.string.all_cities, Icons.Filled.LocationCity)
    object CurrentLocation : MainScreen("current", R.string.current, Icons.Filled.MyLocation)
    object Settings : MainScreen("settings", R.string.settings, Icons.Filled.Settings)
    object About : MainScreen("about", R.string.about, Icons.Filled.Info)
}

@ExperimentalAnimationApi
@Composable
fun Main() {
    val navController = rememberNavController()

    Scaffold(topBar = { MainTopBar(navController) },
        bottomBar = { MainBottomNavigation(navController) },
        floatingActionButton = { MainFloatingActionButton(navController) })
    {
        NavHost(
            navController = navController,
            startDestination = MainScreen.AllWeathers.route
        )
        {
            composable(MainScreen.AllWeathers.route)
            {
            }
            composable(MainScreen.CurrentLocation.route)
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
fun MainBottomNavigation(navController: NavHostController) {
    val bottomBarItems = listOf(
        MainScreen.AllWeathers,
        MainScreen.CurrentLocation,
        MainScreen.Settings
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

        bottomBarItems.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.imageVector, contentDescription = "") },
                label = { Text(text = stringResource(id = screen.nameResourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route)
                    {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                })
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
    Column {
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