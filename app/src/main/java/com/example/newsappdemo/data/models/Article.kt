package com.example.newsappdemo.data.models

data class Article(
    val summary: String,
    val country: String,
    val author: String,
    val link: String,
    val language: String,
    val media: String,
    val title: String,
    val media_content: List<String>,
    val clean_url: String,
    val rights: String,
    val rank: Int,
    val topic: String,
    val published_date: String,
    val _id: String,
    val _score: Double
)