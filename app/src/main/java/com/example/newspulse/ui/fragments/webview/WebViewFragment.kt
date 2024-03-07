package com.example.newspulse.ui.fragments.webview

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.newspulse.data.remote.Article
import com.example.newspulse.databinding.FragmentWebViewBinding
import com.example.newspulse.ui.fragments.news.NewsViewModel
import com.example.newspulse.utils.Constants.URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private var binding: FragmentWebViewBinding? = null
    private var viewModel: NewsViewModel? = null
    private var mViewModel: WebViewViewModel? = null
    private lateinit var mContext: Context
    private var article = Article()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWebViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadWebViewUrl()
    }

    private fun loadWebViewUrl() {
        val url = arguments?.getString(URL)
        binding?.webView?.apply {
            settings.javaScriptEnabled
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    viewModel?.setLoading(true)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    viewModel?.setLoading(true)
                }
            }
            if (url != null) {
                loadUrl(url)
            }
        }
        addToFavorites(article)
    }

    private fun addToFavorites(article: Article) {
        mViewModel?.addToFavorites(article)
        binding?.clickFavoriteButton?.setOnClickListener {
            Toast.makeText(mContext, "Article added to favorites", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

}