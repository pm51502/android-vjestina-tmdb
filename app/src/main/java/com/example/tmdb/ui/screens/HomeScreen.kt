package com.example.tmdb.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.tmdb.ui.navigation.RootScreen
import com.example.tmdb.ui.navigation.navigateToScreen
import com.example.tmdb.ui.screens.shared.components.MovieItemViewState
import com.example.tmdb.ui.screens.shared.components.TabLayout
import com.example.tmdb.ui.screens.shared.components.SearchView
import com.example.tmdb.utils.ConnectionState
import com.example.tmdb.utils.connectivityState
import com.example.tmdb.utils.toDbMovie
import com.example.tmdb.viewmodels.HomeViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel by viewModel<HomeViewModel>()
    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    //val isConnected = checkForInternet(context = LocalContext.current)

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val onMovieItemClick = { movieId: Int ->
        navigateToScreen(
            navController = navController,
            route = "${RootScreen.Details.route}/$movieId"
        )
    }

    val onFavoriteClick = { movie: MovieItemViewState ->
        if (!movie.isFavorite)
            homeViewModel.removeFavoriteMovie(movieId = movie.id)
        else
            homeViewModel.addFavoriteMovie(movie = movie.toDbMovie())

        //homeViewModel.toggleFavorite(movie = movie)
    }

    val popularMovies = homeViewModel.popularMoviesStateFlow.collectAsState().value
    val topRatedMovies = homeViewModel.topRatedMoviesStateFlow.collectAsState().value
    val nowPlayingMovies = homeViewModel.nowPlayingMoviesStateFlow.collectAsState().value
    val upcomingMovies = homeViewModel.upcomingMoviesStateFlow.collectAsState().value

    if (!isConnected) {
        Text(text = "No internet")
    } else {
        LazyColumn {
            item {
                SearchView(state = searchState)
            }

            item {
                TabLayout(
                    tabItems = mapOf(
                        "What's popular" to popularMovies,
                        "Top rated" to topRatedMovies
                    ),
                    title = "What's popular",
                    onMovieItemClick = onMovieItemClick,
                    onFavoriteClick = onFavoriteClick
                )
            }

            item {
                TabLayout(
                    tabItems = mapOf(
                        "Now playing" to nowPlayingMovies
                    ),
                    title = "Now playing",
                    onMovieItemClick = onMovieItemClick,
                    onFavoriteClick = onFavoriteClick
                )
            }

            item {
                TabLayout(
                    tabItems = mapOf(
                        "Upcoming" to upcomingMovies
                    ),
                    title = "Upcoming",
                    onMovieItemClick = onMovieItemClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }

}
