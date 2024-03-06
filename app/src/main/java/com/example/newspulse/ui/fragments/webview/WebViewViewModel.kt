package com.example.newspulse.ui.fragments.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.remote.Article
import com.example.newspulse.repository.webview.WebViewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(private val repository: WebViewRepository) :
    ViewModel() {

    fun addToFavorites(article: Article) {
        viewModelScope.launch {
            repository.addToFavorites(article)
        }
    }

    fun getFavoritesNews() = repository.getSavedNames()

    fun deleteFromFavorites(article: Article) {
        viewModelScope.launch {
            repository.deleteArticles(article)
        }
    }
}