package com.example.tmdb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.R
import com.example.tmdb.ui.navigation.Screen
import com.example.tmdb.ui.navigation.SetupNavHost
import com.example.tmdb.ui.navigation.navigateToScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        Screen.Home,
        Screen.Favorites,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.app_logo),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(35.dp, 0.dp, 110.dp, 7.dp)
                    )
                },
                navigationIcon = {
                    val currentRoute = currentDestination?.route

                    if (currentRoute != null && currentRoute.startsWith(Screen.Details.route)) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(
                                    id = R.string.back_button
                                )
                            )
                        }
                    }
                },
            )
        },

        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.primary
            ) {
                items.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true

                    BottomNavigationItem(
                        icon = { BottomNavigationIcon(screen = screen, isSelected = selected) },
                        label = { Text(text = stringResource(screen.resourceId)) },
                        selected = selected,
                        onClick = {
                            navigateToScreen(navController = navController, route = screen.route)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        SetupNavHost(navController = navController, innerPadding = innerPadding)
    }
}

@Composable
fun BottomNavigationIcon(screen: Screen, isSelected: Boolean) {
    val painter =
        if (screen.route == Screen.Home.route)
            painterResource(id = R.drawable.ic_home_outline)
        else
            painterResource(id = R.drawable.ic_fav_outline)

    val selectedPainter =
        if (screen.route == Screen.Home.route)
            painterResource(id = R.drawable.ic_home)
        else
            painterResource(id = R.drawable.ic_fav)

    if (isSelected) Icon(
        painter = selectedPainter,
        contentDescription = stringResource(id = R.string.bottom_navigation_icon_selected)
    ) else Icon(
        painter = painter,
        contentDescription = stringResource(id = R.string.bottom_navigation_icon)
    )
}
