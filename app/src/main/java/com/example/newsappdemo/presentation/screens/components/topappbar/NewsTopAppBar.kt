package com.example.newsappdemo.presentation.screens.components.topappbar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsappdemo.domain.util.appbar.SearchAppBarState
import com.example.newsappdemo.presentation.screens.newsscreen.NewsViewModel

@ExperimentalMaterial3Api
@Composable
fun NewsTopAppBar(
    viewModel: NewsViewModel = hiltViewModel(),
    scrollBehavior: TopAppBarScrollBehavior
) {
    val searchAppBarState: SearchAppBarState by viewModel.searchAppBarState
    val searchTextState: String by viewModel.searchTextState
    Card(
        shape = RoundedCornerShape(16.dp)
    ) {
        when (searchAppBarState) {
            SearchAppBarState.CLOSED -> {
                DefaultTopAppBar(
                    onSearchClicked = {
                        viewModel.searchAppBarState.value = SearchAppBarState.OPENED
                    },
                    scrollBehavior = scrollBehavior
                )
            }
            else -> {
                SearchTopAppBar(
                    text = searchTextState,
                    onTextChange = { text ->
                        viewModel.searchTextState.value = text
                    },
                    onCloseClicked = {
                        viewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    },
                    onSearchClicked = {
                        viewModel.getNews(q = it)
                    }
                )
            }
        }
    }
}