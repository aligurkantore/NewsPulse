package com.example.newspulse.ui.fragments.search

import androidx.lifecycle.ViewModel
import com.example.newspulse.repository.NewsRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private var repo : NewsRepository) : ViewModel() {
}