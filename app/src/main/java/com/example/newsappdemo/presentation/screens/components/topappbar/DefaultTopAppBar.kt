package com.example.newsappdemo.presentation.screens.components.topappbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.newsappdemo.R

@ExperimentalMaterial3Api
@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.news),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            actions = {
                AppBarActions(
                    onSearchClicked = onSearchClicked
                )
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@Composable
private fun AppBarActions(
    onSearchClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
}

@Composable
private fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = { onSearchClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_icon),
        )
    }
}