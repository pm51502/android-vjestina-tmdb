package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.example.tmdb.R

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    title: String,
    tabItems: Map<String, List<MovieItemViewState>>,
    onMovieItemClick: (movieId: Int) -> Unit,
    onFavoriteClick: (movie: MovieItemViewState) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    val movieTitles = tabItems.keys.toList()

    Column {
        ContentTitle(text = title)

        ScrollableTabRow(
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_md),
                end = dimensionResource(id = R.dimen.padding_md)
            ),
            selectedTabIndex = tabIndex,
            edgePadding = dimensionResource(id = R.dimen.no_padding),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = colorResource(id = R.color.dark_blue),
            indicator = { tabPositions ->
                Box(
                    modifier = modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(dimensionResource(id = R.dimen.tab_indicator_height))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.border_radius_s)))
                        .background(
                            color = colorResource(id = R.color.dark_blue),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.border_radius_s))
                        )
                )
            },
            divider = {},
        ) {
            movieTitles.forEachIndexed { index, item ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    selectedContentColor = colorResource(id = R.color.dark_blue),
                    unselectedContentColor = colorResource(id = R.color.gray_3),
                    text = {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.h4
                        )
                    },

                )
            }
        }
        ListDivider()

        val movieItems: List<MovieItemViewState>? = tabItems[movieTitles[tabIndex]]
        if(movieItems != null)
            MoviesList(
                movieItems = movieItems,
                onMovieItemClick = onMovieItemClick,
                onFavoriteClick = onFavoriteClick
            )

        ListDivider()
    }
}

@Composable
fun ListDivider(){
    Divider(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_xsm))
    )
}
