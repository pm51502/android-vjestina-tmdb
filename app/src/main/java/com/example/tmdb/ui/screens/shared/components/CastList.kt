package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.tmdb.R
import com.example.tmdb.utils.Cast

@Composable
fun CastList(
    modifier: Modifier = Modifier,
    castItems: List<Cast>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.home_movies_list_content_padding),
        )
    ) {
        items(castItems.size) { index ->
            val castItem = castItems[index]
            CastCard(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.micro_spacing)),
                item = castItem
            )
        }
    }
}
