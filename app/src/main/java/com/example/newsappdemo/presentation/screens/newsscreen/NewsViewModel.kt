package com.example.newsappdemo.presentation.screens.newsscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappdemo.data.models.Article
import com.example.newsappdemo.domain.repository.NewsRepository
import com.example.newsappdemo.domain.util.appbar.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> = mutableStateOf("")

    var news: Flow<PagingData<Article>> = emptyFlow()

    private val _itemIds = MutableStateFlow(listOf<Int>())
    val itemIds: StateFlow<List<Int>> = _itemIds

    val locale: MutableState<String> = mutableStateOf("en")

    fun getNews(
        q: String
    ) {
        news = repository.getNews(
            query = q,
            lang = locale.value
        ).cachedIn(viewModelScope)
    }

    fun onItemClicked(
        itemId: Int
    ) {
        _itemIds.value = _itemIds.value.toMutableList().also { list ->
            if (list.contains(itemId)) {
                list.remove(itemId)
            } else {
                list.add(itemId)
            }
        }
    }
}