package com.example.newspulse.ui.fragments.news

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspulse.R
import com.example.newspulse.adapter.NewsAdapter
import com.example.newspulse.data.remote.Article
import com.example.newspulse.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null
    private var viewModel: NewsViewModel? = null
    private var adapter: NewsAdapter? = null
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        searchNews()
        setObserver()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private fun searchNews() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(500)
                        viewModel?.query?.postValue(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(500)
                        viewModel?.query?.postValue(newText)
                    }
                }
                return true
            }
        })

        binding?.searchView?.setOnCloseListener {
            viewModel?.loadOriginalData()
            true
        }
    }

    private fun setObserver() {
        viewModel?.newsLiveData?.observe(viewLifecycleOwner) { newsDataResponse ->
            newsDataResponse?.articles?.let { articles ->
                setAdapter(articles)
                setSpinnerAdapter(articles)
            }
        }

        viewModel?.isLoading?.observe(viewLifecycleOwner) { loading ->
            binding?.progressBar?.visibility = if (loading == true) View.VISIBLE else View.GONE
        }

        viewModel?.searchNewsLiveData?.observe(viewLifecycleOwner) { searchNewsDataResponse ->
            searchNewsDataResponse?.articles?.let { setAdapter(it) }
        }

        viewModel?.query?.observe(viewLifecycleOwner) { query ->
            query?.let { viewModel?.searchNewsList(it, 1) }
        }
    }

    private fun setAdapter(data: List<Article>) {
        adapter = NewsAdapter(data, object : NewsAdapter.ItemClickListener {
            override fun onClick(data: Article) {
                viewModel?.setLoading(true)
                val bundle = Bundle().apply {
                    putString("url", data.url)
                }
                findNavController().navigate(R.id.action_newsFragment_to_webViewFragment, bundle)
            }
        })
        binding?.apply {
            recyclerViewNews.adapter = adapter
            recyclerViewNews.layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun setSpinnerAdapter(articles: List<Article>) {
        binding?.spinnerCountries?.adapter = ArrayAdapter(
            mContext, android.R.layout.simple_spinner_item,
            mutableListOf(getString(R.string.login)) + (articles.mapNotNull {
                it.author
            })
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding?.spinnerCountries?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0){
                    Toast.makeText(mContext, "You have to choice Author", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding?.progressBar?.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}