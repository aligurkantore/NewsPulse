package com.example.newspulse.ui.fragments.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.remote.NewsDataResponse
import com.example.newspulse.repository.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private var repository : SearchRepository) : ViewModel() {

    private var job : Job? = null
    val searchNewsLiveData = MutableLiveData<NewsDataResponse>()
    val query = MutableLiveData<String>().also { it.value = null }


    fun searchNewsList(query : String, page : Int){
        job = viewModelScope.launch(Dispatchers.IO){
            val response = repository.getSearchNewsList(query,page)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        searchNewsLiveData.postValue(it)
                    }
                }
            }
        }
    }
}