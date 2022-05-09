package com.example.tmdb.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.navigation.RootScreen
import com.example.tmdb.ui.navigation.navigateToScreen
import com.example.tmdb.ui.screens.shared.components.TabLayout
import com.example.tmdb.ui.screens.shared.components.SearchView
import com.example.tmdb.utils.MovieLoader
import com.example.tmdb.viewmodels.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel

@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel by viewModel<HomeViewModel>()

    val searchState = remember { mutableStateOf(TextFieldValue("")) }

    /*var allMovies by remember {
        mutableStateOf(MovieLoader.allMovies)
    }*/

    val onMovieItemClick = { movieId: Int ->
        navigateToScreen(
            navController = navController,
            route = "${RootScreen.Details.route}/$movieId"
        )
    }

    val coroutineScope = rememberCoroutineScope()

    val onFavoriteClick = { movieId: Int ->
        coroutineScope.launch {
            homeViewModel.toggleFavorite(movieId = movieId)
        }

        /*val movie = allMovies.first { it.id == movieId }
        val index = allMovies.indexOf(movie)
        val updatedMovie = movie.copy(isFavorite = movie.isFavorite.not())

        allMovies = allMovies.toMutableList().apply { set(index, updatedMovie) }*/
    }

    val popularMovies = homeViewModel.popularMoviesStateFlow.collectAsState()
    val topRatedMovies = homeViewModel.topRatedMoviesStateFlow.collectAsState()
    val nowPlayingMovies = homeViewModel.nowPlayingMoviesStateFlow.collectAsState()
    val upcomingMovies = homeViewModel.upcomingMoviesStateFlow.collectAsState()

    LazyColumn {
        item {
            SearchView(state = searchState)
        }

        item {
            TabLayout(
                tabItems = mapOf(
                    "What's popular" to popularMovies.value,
                    "Top rated" to topRatedMovies.value
                ),
                title = "What's popular",
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }

        item {
            TabLayout(
                tabItems = mapOf(
                    "Now playing" to nowPlayingMovies.value
                ),
                title = "Now playing",
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }

        item {
            TabLayout(
                tabItems = mapOf(
                    "Upcoming" to upcomingMovies.value
                ),
                title = "Upcoming",
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }

}

/*@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}*/
