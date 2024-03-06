package com.example.newspulse.ui.fragments.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspulse.R
import com.example.newspulse.adapter.NewsAdapter
import com.example.newspulse.data.remote.Article
import com.example.newspulse.databinding.FragmentSearchBinding
import com.example.newspulse.ui.fragments.news.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private var adapter: NewsAdapter? = null
    private var viewModel: SearchViewModel? = null
    private lateinit var mContext: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        searchNewsList()
        observeData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    fun searchNewsList() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    lifecycleScope.launch {
                        delay(500)
                        viewModel?.query?.postValue(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    lifecycleScope.launch {
                        delay(500)
                        viewModel?.query?.postValue(newText)
                    }
                }
                return true
            }

        })
    }

    fun observeData() {
        viewModel?.searchNewsLiveData?.observe(viewLifecycleOwner) { newsDataResponse ->
            newsDataResponse?.let {
                it.articles?.let { it1 -> setAdapter(it1) }
            }
        }

        viewModel?.query?.observe(viewLifecycleOwner) { query ->
            query?.let {
                viewModel?.searchNewsList(it, 1)
            }
        }
    }

    fun setAdapter(data: List<Article>) {
        adapter = NewsAdapter(data, object : NewsAdapter.ItemClickListener {
            override fun onClick(data: Article) {
                val bundle = Bundle().apply {
                    putString("url", data.url)
                }
                findNavController().navigate(R.id.action_searchFragment_to_webViewFragment, bundle)
            }
        })
        binding?.apply {
            recyclerViewNews.adapter = adapter
            recyclerViewNews.layoutManager = LinearLayoutManager(mContext)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}