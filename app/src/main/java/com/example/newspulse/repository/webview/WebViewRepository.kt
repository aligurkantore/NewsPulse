package com.example.newspulse.repository.webview

import com.example.newspulse.data.remote.Article
import com.example.newspulse.db.ArticleDAO
import javax.inject.Inject

class WebViewRepository @Inject constructor(private val articleDAO: ArticleDAO) {

    suspend fun addToFavorites(article: Article) {
        articleDAO.upsert(article)
    }

    fun getSavedNames() {
        articleDAO.getAllArticles()
    }

    suspend fun deleteArticles(article: Article) {
        articleDAO.deleteArticle(article)
    }
}