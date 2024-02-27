package com.example.newspulse.repository

import com.example.newspulse.api.ApiService
import com.example.newspulse.utils.Contants.API_KEY
import com.example.newspulse.utils.Contants.COUNTRY
import javax.inject.Inject

class NewsRepository @Inject constructor(private var service: ApiService) {

    suspend fun getNewsList(page: Int) =
        service.getHeadlines(COUNTRY, page, API_KEY)

    suspend fun getSearchNewsList(searchQuery: String, page: Int, apiKey: String) =
        service.searchNews(searchQuery, page, apiKey)

}