package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.ui.navigation.RootScreen

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    rootNavController: NavController
) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(35.dp, 0.dp, 110.dp, 7.dp)
            )
        },
        navigationIcon = {
            if (currentRoute != null && currentRoute.startsWith(RootScreen.Details.route)) {
                IconButton(onClick = { rootNavController.navigateUp() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        },
    )
}
