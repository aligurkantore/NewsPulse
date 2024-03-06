package com.example.newspulse.data.remote

data class NewsDataResponse(
    val articles: List<Article>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)