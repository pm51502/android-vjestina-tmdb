package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.tmdb.R


@Composable
fun ContentTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        modifier = modifier.padding(
            start = dimensionResource(id = R.dimen.padding_md),
            top = dimensionResource(id = R.dimen.padding_md),
            end = dimensionResource(id = R.dimen.padding_md)
        ),
        style = MaterialTheme.typography.h3
    )
}