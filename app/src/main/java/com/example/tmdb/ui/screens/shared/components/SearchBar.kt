package com.example.tmdb.ui.screens.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.tmdb.R

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    val smallPadding = dimensionResource(id = R.dimen.padding_sm)

    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .padding(
                start = smallPadding,
                top = smallPadding,
                end = smallPadding
            )
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(id = R.string.search_icon),
                modifier = Modifier
                    .padding(smallPadding)
                    .size(dimensionResource(id = R.dimen.icon_size))
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.border_radius)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(id = R.color.light_gray),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = stringResource(id = R.string.search)) }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}