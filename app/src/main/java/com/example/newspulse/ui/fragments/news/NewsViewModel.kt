package com.example.newspulse.ui.fragments.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.remote.NewsDataResponse
import com.example.newspulse.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private var repo : NewsRepository) : ViewModel(){

    private var job : Job? = null
    var newsLiveData = MutableLiveData<NewsDataResponse>()


    fun getNewsList(pageNumber : Int){
        job = viewModelScope.launch(Dispatchers.IO){
            val response = repo.getNewsList(pageNumber)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        newsLiveData.postValue(it)
                    }
                }
            }
        }
    }
}