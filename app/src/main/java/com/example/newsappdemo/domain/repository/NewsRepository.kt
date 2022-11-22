package com.example.newsappdemo.domain.repository

import androidx.paging.PagingData
import com.example.newsappdemo.data.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(query: String, lang: String): Flow<PagingData<Article>>
}