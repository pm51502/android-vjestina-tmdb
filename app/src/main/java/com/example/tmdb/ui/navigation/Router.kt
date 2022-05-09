package com.example.tmdb.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tmdb.R
import com.example.tmdb.ui.screens.MainScreen
import com.example.tmdb.ui.screens.DetailsScreen
import com.example.tmdb.ui.screens.FavoritesScreen
import com.example.tmdb.ui.screens.HomeScreen

sealed class RootScreen(val route: String, @StringRes val resourceId: Int) {
    object Main : RootScreen(route = "main", R.string.main)
    object Details : RootScreen(route = "details", R.string.details)
}

sealed class BottomBarScreen(val route: String, @StringRes val resourceId: Int) {
    object Home : BottomBarScreen(route = "home", R.string.home)
    object Favorites : BottomBarScreen(route = "favorites", R.string.favorites)
}

@Composable
fun RootNavHost() {
    val rootNavController = rememberNavController()
    val bottomBarNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = RootScreen.Main.route
    ) {
        composable(
            route = RootScreen.Main.route,
            content = { MainScreen(
                rootNavController = rootNavController,
                bottomBarNavController = bottomBarNavController
            ) })

        composable(
            route = "${RootScreen.Details.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailsScreen(
                rootNavController = rootNavController,
                movieId = backStackEntry.arguments?.getInt("movieId")
            )
        }
    }
}

@Composable
fun BottomBarNavHost(
    rootNavController: NavController,
    bottomBarNavController: NavController
) {
    NavHost(
        navController = bottomBarNavController as NavHostController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(BottomBarScreen.Home.route) { HomeScreen(navController = rootNavController) }
        composable(BottomBarScreen.Favorites.route) { FavoritesScreen(navController = rootNavController) }
    }
}

fun navigateToScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
