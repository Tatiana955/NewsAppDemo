package com.example.newsappdemo.data.models

data class UserInput(
    val q: String,
    val search_in: String,
    val lang: String,
    val ranked_only: String,
    val sort_by: String,
    val from: String,
    val page: Int,
    val size: Int,
    val media: String
)