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
import androidx.compose.ui.tooling.preview.Preview
import com.example.tmdb.R
import com.example.tmdb.utils.Cast

@Composable
fun CastCard(
    modifier: Modifier = Modifier,
    item: Cast
) {
    Card {
        Image(
            painter = painterResource(id = item.imagePath),
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

@Preview
@Composable
fun CastCardPreview(){
    CastCard(item = Cast(
        name = "Chiwetel Ejiofor",
        character = "Scar (voice)",
        imagePath = R.drawable.actor_1
    ))
}
