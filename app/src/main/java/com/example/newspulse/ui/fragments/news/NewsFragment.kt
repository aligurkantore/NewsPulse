package com.example.newspulse.ui.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspulse.R
import com.example.newspulse.adapter.NewsAdapter
import com.example.newspulse.data.remote.Article
import com.example.newspulse.data.remote.NewsDataResponse
import com.example.newspulse.databinding.FragmentNewsBinding
import com.example.newspulse.utils.Contants.PAGE_NUMBER
import com.example.newspulse.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var binding : FragmentNewsBinding? = null
    private var viewModel : NewsViewModel? = null
    private var adapter : NewsAdapter? = null

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
        viewModel?.getNewsList(PAGE_NUMBER)
        setObserver()
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private fun setObserver(){
        viewModel?.newsLiveData?.observe(viewLifecycleOwner){
            setAdapter(it.articles)
        }
    }

    private fun setAdapter(data : List<Article>){
        adapter = NewsAdapter(data, object : NewsAdapter.ItemClickListener{
            override fun onClick(data: Article) {
                val bundle = Bundle()
                bundle.putString("url",data.url)
                findNavController().navigate(R.id.action_newsFragment_to_webViewFragment,bundle)
            }
        })
        binding?.apply {
            recyclerViewNews.adapter = adapter
            recyclerViewNews.layoutManager = LinearLayoutManager(context)
        }
    }
}