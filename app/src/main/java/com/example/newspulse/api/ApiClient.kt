package com.example.newspulse.api

import com.example.newspulse.utils.Contants.BASE_URL
import com.example.newspulse.utils.Contants.TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    @Singleton
    @Provides
    fun create() : ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun client(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            retryOnConnectionFailure(false)
            callTimeout(TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            this.build()
        }
    }
}