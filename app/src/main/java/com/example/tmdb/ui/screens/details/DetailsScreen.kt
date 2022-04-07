package com.example.tmdb.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.R
import com.example.tmdb.ui.screens.shared.components.CastList
import com.example.tmdb.ui.screens.shared.components.ContentTitle
import com.example.tmdb.ui.screens.shared.components.FavouriteButton
import com.example.tmdb.utils.MovieLoader
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    movieId: Int?
) {

    var movieDetails by remember {
        mutableStateOf(
            MovieLoader.getMovieDetails(
                movieId = (movieId ?: 0)
            )
        )
    }

    LazyColumn {
        item {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.details_image_height))
            ) {
                Image(
                    painter = painterResource(id = movieDetails.imagePath),
                    contentDescription = stringResource(id = R.string.details_image),
                    alignment = Alignment.TopStart,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .matchParentSize()
                        .drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = size.height / 3,
                                endY = size.height
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        }
                )

                Column(
                    modifier = modifier.padding(
                        start = dimensionResource(id = R.dimen.details_column_start_padding),
                        top = dimensionResource(id = R.dimen.details_column_top_padding)
                    )
                ) {
                    UserScore(userScore = movieDetails.userScore)

                    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_m)))

                    Row {
                        Text(
                            text = movieDetails.title,
                            style = MaterialTheme.typography.h2
                        )

                        Text(
                            text = "(${movieDetails.year})",
                            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_xxsm)),
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_m)))

                    Text(
                        text = "${movieDetails.date} (${movieDetails.country})",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_s)))

                    Text(
                        text = "${movieDetails.genres.joinToString()} ${movieDetails.duration}",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_xl)))

                    FavouriteButton(
                        movieId = movieDetails.id,
                        isFavorite = movieDetails.isFavorite,
                        onFavoriteClick = {
                            movieDetails =
                                movieDetails.copy().apply { isFavorite = isFavorite.not() }
                        }
                    )
                }
            }
        }

        item {
            Column {
                ContentTitle(text = stringResource(id = R.string.overview))

                Text(
                    text = movieDetails.overview,
                    modifier = modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_md),
                        top = dimensionResource(id = R.dimen.padding_xsm),
                        end = dimensionResource(id = R.dimen.padding_md),
                        bottom = dimensionResource(id = R.dimen.padding_md)
                    )
                )
            }
        }

         item {
            FlowRow(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_md),
                ),
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.Start,
                mainAxisSpacing = dimensionResource(id = R.dimen.padding_md),
                crossAxisSpacing = dimensionResource(id = R.dimen.padding_l)
            ) {
                movieDetails.crew.forEach { (key, value) ->
                    value.forEach { v ->
                        CrewMember(name = v, crew = key)
                    }
                }
            }
        }

        item { 
            Column {
                ContentTitle(text = stringResource(id = R.string.top_billed_cast))
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_xxl)))
                CastList(castItems = movieDetails.topBilledCast)
            }
        }
    }
}

@Composable
fun UserScore(
    modifier: Modifier = Modifier,
    userScore: Float
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box {
            CircularProgressIndicator(
                progress = userScore,
                modifier = modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_xxsm))
                    .size(dimensionResource(id = R.dimen.circular_progress_bar_size)),
                color = Color.Green,
                strokeWidth = dimensionResource(id = R.dimen.circular_progress_bar_width)
            )

            Text(
                text = "${(userScore * 100).toInt()}%",
                modifier = modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_xmd),
                    top = dimensionResource(id = R.dimen.padding_sm)
                ),
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }

        Text(
            text = stringResource(id = R.string.user_score),
            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_xsm)),
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
fun CrewMember(
    modifier: Modifier = Modifier,
    name: String,
    crew: String
) {
    Column(
        modifier = modifier.width(dimensionResource(id = R.dimen.crew_member_column_width))
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.ExtraBold
        )
        Text(text = crew)
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        navController = rememberNavController(),
        movieId = 1
    )
}
