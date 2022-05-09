package com.example.tmdb.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.R
import com.example.tmdb.ui.navigation.RootScreen
import com.example.tmdb.ui.navigation.navigateToScreen
import com.example.tmdb.ui.screens.shared.components.ContentTitle
import com.example.tmdb.ui.screens.shared.components.MovieCard
import com.example.tmdb.utils.MovieLoader
import com.example.tmdb.viewmodels.FavoritesViewModel
import com.example.tmdb.viewmodels.HomeViewModel
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel

@Composable
fun FavoritesScreen(navController: NavController) {
    val favoritesViewModel by viewModel<FavoritesViewModel>()

    /*var favoriteMovies by remember {
        mutableStateOf(MovieLoader.getFavoriteMovies())
    }*/

    val favoriteMovies = favoritesViewModel.favoriteMoviesStateFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        item {
            ContentTitle(text = stringResource(id = R.string.favorites))
        }

        item {
            FlowRow(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.favorites_movies_list_content_padding),
                    top = dimensionResource(id = R.dimen.padding_md)
                ),
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.Start,
                mainAxisSpacing = dimensionResource(id = R.dimen.padding_md),
                crossAxisSpacing = dimensionResource(id = R.dimen.padding_l)
            ) {
                favoriteMovies.value.forEach { item ->
                    MovieCard(
                        item = item,
                        modifier = Modifier.size(
                            width = dimensionResource(id = R.dimen.favorite_movie_card_width),
                            height = dimensionResource(id = R.dimen.favorite_movie_card_height)
                        ),
                        onMovieItemClick = { movieId: Int ->
                            navigateToScreen(
                                navController = navController,
                                route = "${RootScreen.Details.route}/$movieId"
                            )
                        },
                        onFavoriteClick = { movieId: Int ->

                            coroutineScope.launch {
                                favoritesViewModel.toggleFavorite(movieId = movieId)
                            }

                            /*val movie = favoriteMovies.first { it.id == movieId }
                            val index = favoriteMovies.indexOf(movie)
                            val updatedMovie = movie.copy(isFavorite = movie.isFavorite.not())

                            favoriteMovies = favoriteMovies.toMutableList().apply { set(index, updatedMovie) }*/
                        }
                    )
                }
            }
        }
    }
}

/*@Preview
@Composable
fun FavoritesScreenPreview() {
    val navController = rememberNavController()
    FavoritesScreen(navController = navController)
}*/
