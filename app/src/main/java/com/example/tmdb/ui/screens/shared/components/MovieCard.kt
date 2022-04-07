package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tmdb.R

data class MovieItemViewState(
    val id: Int,
    val title: String,
    val overview: String,
    val imagePath: Int,
    var isFavorite: Boolean
    //val imagePath: String
)

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    item: MovieItemViewState,
    onMovieItemClick: (movieId: Int) -> Unit,
    onFavoriteClick: (movieId: Int) -> Unit
) {

    Box(
        modifier = modifier.clickable {
            onMovieItemClick.invoke(item.id)
        }
    ) {

        Image(
            painter = painterResource(id = item.imagePath),
            contentDescription = null,
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.movie_card_width),
                    height = dimensionResource(id = R.dimen.movie_card_height)
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.border_radius))),
            contentScale = ContentScale.Crop
        )
        FavouriteButton(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.small_spacing),
                top = dimensionResource(id = R.dimen.small_spacing)
            ),
            movieId = item.id,
            isFavorite = item.isFavorite,
            onFavoriteClick = onFavoriteClick
        )
    }
}


@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(
        item = MovieItemViewState(
            id = 1,
            title = "Iron man",
            overview = "",
            imagePath = R.drawable.iron_man_1,
            isFavorite = true
        ),
        onFavoriteClick = {},
        onMovieItemClick = {}
    )
}
