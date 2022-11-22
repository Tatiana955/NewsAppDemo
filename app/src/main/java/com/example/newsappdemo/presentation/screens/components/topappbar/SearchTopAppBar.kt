package com.example.newsappdemo.presentation.screens.components.topappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.newsappdemo.R
import com.example.newsappdemo.domain.util.appbar.IconState

@ExperimentalMaterial3Api
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var iconState by remember { mutableStateOf(IconState.DELETE) }
    Surface(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        TextField(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(text = stringResource(R.string.search))
            },
            textStyle = MaterialTheme.typography.labelLarge,
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onSearchClicked(text)
                        onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        when (iconState) {
                            IconState.DELETE -> {
                                iconState = if (text.isEmpty()) {
                                    onCloseClicked()
                                    IconState.CLOSE
                                } else {
                                    onTextChange("")
                                    IconState.CLOSE
                                }
                            }
                            IconState.CLOSE -> {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onCloseClicked()
                                    iconState = IconState.DELETE
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    onCloseClicked()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}