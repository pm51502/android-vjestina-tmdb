package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.tmdb.R
import com.example.tmdb.data.CastMember

@Composable
fun CastCard(
    modifier: Modifier = Modifier,
    item: CastMember
) {
    Card {
        Image(
            painter = rememberAsyncImagePainter(
                model = item.profilePath,
                error = painterResource(
                    id = R.drawable.cast_placeholder
                )
            ),
            contentDescription = stringResource(id = R.string.cast_card_image),
            modifier = modifier
                .size(
                    width = dimensionResource(id = R.dimen.cast_card_width),
                    height = dimensionResource(id = R.dimen.cast_card_height)
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.border_radius))),
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = modifier
                .padding(top = dimensionResource(id = R.dimen.cast_card_top_surface_padding))
                .width(dimensionResource(id = R.dimen.cast_card_width))
        ) {
            Column {
                Text(
                    text = item.name,
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_xxsm)),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = item.character,
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_xxsm))
                )
            }
        }
    }
}
