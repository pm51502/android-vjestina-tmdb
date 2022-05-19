package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.tmdb.R
import com.example.tmdb.data.MovieItem
import com.example.tmdb.data.toMovieItem

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    item: MovieItemViewState,
    onFavoriteClick: (movie: MovieItem) -> Unit
) {
    Image(
        painter = painterResource(id = if (item.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite),
        contentDescription = null,
        modifier = modifier
            .clickable {
                onFavoriteClick.invoke(item.toMovieItem())
            }
            .size(dimensionResource(id = R.dimen.large_spacing))
            .background(
                color = colorResource(id = R.color.dark_blue_60),
                CircleShape
            )
            .padding(dimensionResource(id = R.dimen.small_spacing))
    )
}
