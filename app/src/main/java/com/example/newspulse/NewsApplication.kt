package com.example.newspulse

import android.app.Application
import com.example.newspulse.db.ArticleDAO
import com.example.newspulse.db.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class NewsApplication : Application() {
    @InstallIn(SingletonComponent::class)
    @Module
    object AppModule {
        @Provides
        @Singleton
        fun provideArticleDAO(application: Application): ArticleDAO {
            return ArticleDataBase(application).getArticleDao()
        }
    }
}