package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tmdb.R
import kotlinx.coroutines.Job

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    movieItems: List<MovieItem>,
    onMovieItemClick: (movieId: Int) -> Unit,
    onFavoriteClick: (movieId: Int) -> Job
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.home_movies_list_content_padding),
        )
    ) {
        items(movieItems.size) { index ->
            val movieItem = movieItems[index]
            MovieCard(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.micro_spacing)),
                item = movieItem,
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

/*@Preview
@Composable
fun MoviesListPreview() {
    MoviesList(
        movieItems = listOf(
            MovieItem(
                id = 1,
                title = "Iron man",
                overview = "",
                imagePath = R.drawable.iron_man_1,
                isFavorite = true
            ),
            MovieItem(
                id = 2,
                title = "Iron man",
                overview = "",
                imagePath = R.drawable.iron_man_1,
                isFavorite = true
            ),
            MovieItem(
                id = 3,
                title = "Iron man",
                overview = "",
                imagePath = R.drawable.iron_man_1,
                isFavorite = true
            )
        ),
        onFavoriteClick = {},
        onMovieItemClick = {}
    )
}*/
