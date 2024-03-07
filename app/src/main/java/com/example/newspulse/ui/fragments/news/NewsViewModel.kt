package com.example.newspulse.ui.fragments.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.remote.NewsDataResponse
import com.example.newspulse.repository.news.NewsRepository
import com.example.newspulse.utils.Constants.PAGE_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private var repository : NewsRepository) : ViewModel(){

    private val _newsLiveData = MutableLiveData<NewsDataResponse>()
    val newsLiveData: LiveData<NewsDataResponse> = _newsLiveData

   private val _searchNewsLiveData = MutableLiveData<NewsDataResponse>()
    val searchNewsLiveData: LiveData<NewsDataResponse> = _searchNewsLiveData

    val query = MutableLiveData<String>().also { it.value = null }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    init {
        getNewsList(PAGE_NUMBER)
    }

    private fun getNewsList(pageNumber : Int){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.getNewsList(pageNumber)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        _newsLiveData.postValue(it)
                        Log.d("agt", "getNewsList:${response.body()} ")
                    }
                }
            }
        }
    }

    fun searchNewsList(query : String, page : Int){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.getSearchNewsList(query,page)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        _searchNewsLiveData.postValue(it)
                    }
                }
            }
        }
    }

    fun loadOriginalData() {
        getNewsList(PAGE_NUMBER)
    }

}