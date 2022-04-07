package com.example.tmdb.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.navigation.Screen
import com.example.tmdb.ui.navigation.navigateToScreen
import com.example.tmdb.ui.screens.shared.components.TabLayout
import com.example.tmdb.ui.screens.shared.components.SearchView
import com.example.tmdb.utils.MovieLoader

@Composable
fun HomeScreen(navController: NavHostController) {
    val searchState = remember { mutableStateOf(TextFieldValue("")) }

    var allMovies by remember {
        mutableStateOf(MovieLoader.allMovies)
    }

    val onMovieItemClick = { movieId: Int ->
        navigateToScreen(
            navController = navController,
            route = "${Screen.Details.route}/$movieId"
        )
    }

    /*val onFavoriteClick = { updatedMovie: MovieItemViewState ->
        val index = allMovies.indexOfFirst { it.id == updatedMovie.id }
        allMovies = allMovies.toMutableList().apply { set(index, updatedMovie) }
    }*/

    val onFavoriteClick = { movieId: Int ->
        val movie = allMovies.first { it.id == movieId }
        val index = allMovies.indexOf(movie)
        val updatedMovie = movie.copy(isFavorite = movie.isFavorite.not())

        allMovies = allMovies.toMutableList().apply { set(index, updatedMovie) }
    }

    LazyColumn {
        item { 
            SearchView(state = searchState)
        }

        item {
            TabLayout(
                tabItems = mapOf(
                    "What's popular" to allMovies.slice(0..2).toList(),
                    "Top rated" to allMovies.slice(3..5).toList()
                ),
                title = "What's popular",
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }

        item {
            TabLayout(
                tabItems = mapOf(
                    "Now playing" to allMovies.slice(6..8).toList()
                ),
                title = "Now playing",
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }

        item {
            TabLayout(
                tabItems = mapOf(
                    "Upcoming" to allMovies.slice(9..11).toList()
                ),
                title = "Upcoming",
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
