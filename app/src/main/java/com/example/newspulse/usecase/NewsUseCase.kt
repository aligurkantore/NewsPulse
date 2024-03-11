package com.example.newspulse.usecase

import com.bumptech.glide.load.engine.Resource
import com.example.newspulse.data.remote.NewsDataResponse
import com.example.newspulse.repository.news.NewsRepository
import com.example.newspulse.utils.Constants.PAGE_NUMBER

class NewsUseCase(private val repository: NewsRepository) {

    suspend fun getNews(pageNumber: Int): com.example.newspulse.utils.Resource<NewsDataResponse> {
       return getNews(PAGE_NUMBER)
    }
}