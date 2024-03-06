package com.example.newspulse.repository.news

import com.example.newspulse.api.ApiService
import com.example.newspulse.utils.Constants
import com.example.newspulse.utils.Constants.API_KEY
import com.example.newspulse.utils.Constants.COUNTRY
import javax.inject.Inject

class NewsRepository @Inject constructor(private var service: ApiService) {
    suspend fun getNewsList(page: Int) =
        service.getHeadlines(COUNTRY, page, API_KEY)

    suspend fun getSearchNewsList(searchQuery: String, page: Int) =
        service.searchNews(searchQuery, page, Constants.API_KEY)

}