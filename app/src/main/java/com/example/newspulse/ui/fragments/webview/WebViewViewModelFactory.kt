package com.example.newspulse.ui.fragments.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newspulse.repository.webview.WebViewRepository
import javax.inject.Inject

class WebViewViewModelFactory @Inject constructor(private val repository: WebViewRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebViewViewModel::class.java)) {
            return WebViewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}