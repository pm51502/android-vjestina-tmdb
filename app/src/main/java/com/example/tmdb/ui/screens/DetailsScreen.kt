package com.example.tmdb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tmdb.R
import com.example.tmdb.network.CastMember
import com.example.tmdb.network.CrewMember
import com.example.tmdb.network.Genre
import com.example.tmdb.ui.navigation.RootScreen
import com.example.tmdb.ui.screens.shared.components.CastList
import com.example.tmdb.ui.screens.shared.components.ContentTitle
import com.example.tmdb.ui.screens.shared.components.TopBar
import com.example.tmdb.viewmodels.DetailsViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

data class MovieItemDetailViewState(
    val id: Int = 0,
    val userScore: Float = 0F,
    val title: String = "",
    val year: String = "",
    val date: String = "",
    val country: String = "",
    val genres: List<Genre> = emptyList(),
    val runtime: String = "",
    val posterPath: String? = "",
    val overview: String = "",
    val crew: List<CrewMember> = emptyList(),
    val topBilledCast: List<CastMember> = emptyList(),
)

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
    movieId: Int?,
) {
    val detailsViewModel by viewModel<DetailsViewModel> { parametersOf(movieId) }
    val movieDetails = detailsViewModel.movieDetailsStateFlow.collectAsState().value

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                currentRoute = RootScreen.Details.route,
                rootNavController = rootNavController
            )
        }
    ) {
        LazyColumn {
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.details_image_height))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = movieDetails.posterPath),
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
                            text = "${movieDetails.date} (${movieDetails.country.uppercase()})",
                            style = MaterialTheme.typography.subtitle1
                        )

                        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_s)))


                        Text(
                            text = "${movieDetails.genres.joinToString { it.name }} ${movieDetails.runtime}",
                            style = MaterialTheme.typography.subtitle1
                        )

                        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_xl)))

                        StarButton()
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
                CrewRow(crew = movieDetails.crew)
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
                text = "${userScore.toInt()}%",
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
fun CrewMemberViewState(
    modifier: Modifier = Modifier,
    name: String,
    job: String
) {
    Text(
        text = name,
        fontWeight = FontWeight.ExtraBold
    )
    Text(text = job)
}

@Composable
fun StarButton(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = null,
        modifier = modifier
            .clickable {}
            .size(dimensionResource(id = R.dimen.large_spacing))
            .background(
                color = colorResource(id = R.color.dark_blue_60),
                CircleShape
            )
            .padding(dimensionResource(id = R.dimen.small_spacing))
    )
}

@Composable
fun CrewRow(
    modifier: Modifier = Modifier,
    crew: List<CrewMember>
) {
    LazyRow(
        modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_md))
    ) {
        for (i in 0 until crew.size-1 step 2) {
            item {
                Column(
                    modifier = modifier.padding(end = dimensionResource(id = R.dimen.padding_sm))
                ) {
                    CrewMemberViewState(
                        name = crew[i].name,
                        job = crew[i].job
                    )

                    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacer_m)))

                    CrewMemberViewState(
                        name = crew[i + 1].name,
                        job = crew[i + 1].job
                    )
                }
            }
        }
    }
}
