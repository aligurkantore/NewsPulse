package com.example.newspulse.repository.search

import com.example.newspulse.api.ApiService
import com.example.newspulse.utils.Constants
import javax.inject.Inject

class SearchRepository @Inject constructor(private var service: ApiService) {

    suspend fun getSearchNewsList(searchQuery: String, page: Int) =
        service.searchNews(searchQuery, page, Constants.API_KEY)

}