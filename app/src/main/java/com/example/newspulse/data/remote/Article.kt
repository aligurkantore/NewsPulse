package com.example.newspulse.data.remote

import java.io.Serializable

data class Article(
    val author: String? = null,
    val content: Any? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
) : Serializable