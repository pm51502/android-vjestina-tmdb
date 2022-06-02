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
import coil.compose.rememberAsyncImagePainter
import com.example.tmdb.R

data class MovieItemViewState(
    val id: Int,
    val title: String,
    val overview: String,
    val imageUrl: String?,
    var isFavorite: Boolean
)

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    item: MovieItemViewState,
    onMovieItemClick: (movieId: Int) -> Unit,
    onFavoriteClick: (movie: MovieItemViewState) -> Unit
) {

    Box(
        modifier = modifier.clickable {
            onMovieItemClick.invoke(item.id)
        }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.movie_card_width),
                    height = dimensionResource(id = R.dimen.movie_card_height)
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.border_radius))),
            contentScale = ContentScale.Crop
        )
        FavoriteButton(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.small_spacing),
                top = dimensionResource(id = R.dimen.small_spacing)
            ),
            item = item,
            onFavoriteClick = onFavoriteClick
        )
    }
}
