package com.example.newsappdemo.presentation.screens.newsscreen

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsappdemo.R
import com.example.newsappdemo.data.models.Article

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    contentPadding: PaddingValues
) {
    val data = viewModel.news.collectAsLazyPagingItems()
    val currentLocale = Locale.current
    viewModel.locale.value = currentLocale.language
    Content(
        modifier = modifier,
        viewModel = viewModel,
        data = data,
        contentPadding = contentPadding
    )
}

@Composable
private fun Content(
    modifier: Modifier,
    viewModel: NewsViewModel,
    data: LazyPagingItems<Article>,
    contentPadding: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        News(
            modifier = modifier,
            viewModel = viewModel,
            data = data,
            contentPadding = contentPadding
        )
    }
}

@Composable
private fun News(
    modifier: Modifier,
    viewModel: NewsViewModel,
    data: LazyPagingItems<Article>,
    contentPadding: PaddingValues
) {
    val itemIds by viewModel.itemIds.collectAsState()
    val error = stringResource(R.string.error_429)
    val errorStyle = MaterialTheme.typography.labelLarge
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = data.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading(modifier = modifier)
            }
            is LoadState.Error -> {
                Error(
                    message = state.error.message ?: "",
                    style = errorStyle
                )
            }
        }
        when (data.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                if (viewModel.searchTextState.value == "")
                    NoDataToSearch(modifier = modifier)
                else
                    Loading(modifier = modifier)
            }
            is LoadState.Error -> {
                Error(
                    message = error,
                    style = errorStyle
                )
            }
        }
        itemsIndexed(items = data) { index, item ->
            ExpandableContainer(
                modifier = modifier,
                data = item,
                onClickItem = { viewModel.onItemClicked(index) },
                expanded = itemIds.contains(index)
            )
        }
        when (data.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading(modifier = modifier)
            }
            is LoadState.Error -> {
                Error(
                    message = error,
                    style = errorStyle
                )
            }
        }
    }
}

@Composable
private fun ExpandableContainer(
    modifier: Modifier,
    data: Article?,
    onClickItem: () -> Unit,
    expanded: Boolean
) {
    Spacer(modifier = modifier.height(8.dp))
    Card(
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            HeaderNewsItem(
                modifier = modifier,
                data = data,
                onClickItem = onClickItem
            )
            ExpandableNewsItem(
                modifier = modifier,
                data = data,
                isExpanded = expanded
            )
        }
    }
    Spacer(modifier = modifier.height(8.dp))
}

@Composable
private fun HeaderNewsItem(
    modifier: Modifier,
    data: Article?,
    onClickItem: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClickItem
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data?.media ?: R.drawable.ic_insert_photo_24)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.media_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .height(64.dp)
                .width(64.dp)
        )
        Spacer(modifier = modifier.width(16.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = data?.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = data?.published_date?.dropLast(3) ?: "",
                style = MaterialTheme.typography.labelMedium,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ExpandableNewsItem(
    modifier: Modifier,
    data: Article?,
    isExpanded: Boolean
) {
    val context = LocalContext.current
    // Opening Animation
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }
    // Closing Animation
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = data?.summary ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = data?.author ?: "",
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { sharePost(data, context) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = stringResource(R.string.share_icon)
                    )
                }
            }
        }
    }
}

// See: https://developer.android.com/training/sharing/send
private fun sharePost(data: Article?, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, data?.title)
        putExtra(Intent.EXTRA_TEXT, data?.link)
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_post)))
}

private fun LazyListScope.NoDataToSearch(
    modifier: Modifier
) {
    item {
        Image(
            painter = painterResource(R.drawable.ic_news),
            contentDescription = stringResource(R.string.news_image),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary,
                blendMode = BlendMode.Color
            ),
            modifier = modifier
                .fillMaxHeight()
                .clip(CircleShape)
        )
    }
}

private fun LazyListScope.Loading(
    modifier: Modifier
) {
    item {
        CircularProgressIndicator(
            modifier = modifier.padding(16.dp)
        )
    }
}

private fun LazyListScope.Error(
    message: String,
    style: TextStyle
) {
    item {
        Text(
            text = message,
            style = style,
            color = MaterialTheme.colorScheme.error
        )
    }
}