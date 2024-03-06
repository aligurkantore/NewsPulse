package com.example.newspulse.api

import com.example.newspulse.data.remote.NewsDataResponse
import com.example.newspulse.utils.Constants.API_KEY
import com.example.newspulse.utils.Constants.COUNTRY
import com.example.newspulse.utils.Constants.PAGE_NUMBER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode : String = COUNTRY,
        @Query("page") pageNumber : Int = PAGE_NUMBER,
        @Query("apiKey") apiKey : String = API_KEY
    ) : Response<NewsDataResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery : String,
        @Query("page") pageNumber : Int = PAGE_NUMBER,
        @Query("apiKey") apiKey : String = API_KEY
    ) : Response<NewsDataResponse>
}