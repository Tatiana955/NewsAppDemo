package com.example.newsappdemo.presentation.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.newsappdemo.presentation.screens.components.topappbar.NewsTopAppBar
import com.example.newsappdemo.presentation.screens.newsscreen.NewsScreen

@ExperimentalMaterial3Api
@Composable
fun AppScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NewsTopAppBar(
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            NewsScreen(
                contentPadding = innerPadding
            )
        }
    )
}