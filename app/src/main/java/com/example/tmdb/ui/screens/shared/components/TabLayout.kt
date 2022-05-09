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
import androidx.compose.ui.tooling.preview.Preview
import com.example.tmdb.R
import kotlinx.coroutines.Job

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    title: String,
    tabItems: Map<String, List<MovieItem>>,
    onMovieItemClick: (movieId: Int) -> Unit,
    onFavoriteClick: (movieId: Int) -> Job
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

        val movieItems: List<MovieItem>? = tabItems[movieTitles[tabIndex]]
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

/*@Preview
@Composable
fun TabLayoutPreview(){
    TabLayout(
        tabItems = mapOf(
            "Popular" to listOf(
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
            "Top rated" to listOf(
                MovieItem(
                    id = 4,
                    title = "Iron man",
                    overview = "",
                    imagePath = R.drawable.iron_man_1,
                    isFavorite = true
                ),
                MovieItem(
                    id = 5,
                    title = "Iron man",
                    overview = "",
                    imagePath = R.drawable.iron_man_1,
                    isFavorite = true
                ),
                MovieItem(
                    id = 6,
                    title = "Iron man",
                    overview = "",
                    imagePath = R.drawable.iron_man_1,
                    isFavorite = true
                )
            )
        ),
        title = "What's popular",
        onMovieItemClick = {},
        onFavoriteClick = {}
    )
}*/
