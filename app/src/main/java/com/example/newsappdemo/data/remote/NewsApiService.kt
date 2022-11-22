package com.example.newsappdemo.data.remote

import com.example.newsappdemo.data.models.News
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v1/search_free")
    suspend fun getNews(
        @Header("X-RapidAPI-Key") app_key: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("lang") lang: String,
        @Query("media") media: String
    ): News
}