package com.example.tmdb.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tmdb.R
import com.example.tmdb.ui.screens.details.DetailsScreen
import com.example.tmdb.ui.screens.favourites.FavoritesScreen
import com.example.tmdb.ui.screens.home.HomeScreen

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Favorites : Screen("favorites", R.string.favorites)
    object Details : Screen("details", R.string.details)
}

@Composable
fun SetupNavHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
        composable(route = Screen.Favorites.route) { FavoritesScreen(navController = navController) }
        composable(
            route = "${Screen.Details.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailsScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getInt("movieId")
            )
        }
    }
}

fun navigateToScreen(navController: NavHostController, route: String) {
    navController.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        /*popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }*/
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
