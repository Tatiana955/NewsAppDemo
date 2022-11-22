package com.example.newsappdemo.data.models

data class News(
    val status: String,
    val total_hits: Int,
    val page: Int,
    val total_pages: Int,
    val page_size: Int,
    val articles: List<Article>,
    val user_input: UserInput
)