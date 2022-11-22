package com.example.newsappdemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsappdemo.data.models.Article
import com.example.newsappdemo.data.remote.NewsApiService
import com.example.newsappdemo.domain.paging.NewsPagingSource
import com.example.newsappdemo.domain.repository.NewsRepository
import com.example.newsappdemo.domain.util.Constants.STARTING_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiService
): NewsRepository {

    override fun getNews(
        query: String,
        lang: String
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = STARTING_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NewsPagingSource(
                    apiService = api,
                    q = query,
                    lang = lang
                )
            }
        ).flow
    }
}