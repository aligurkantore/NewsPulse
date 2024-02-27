package com.example.newspulse.ui.fragments.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.newspulse.R
import com.example.newspulse.data.remote.Article
import com.example.newspulse.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private lateinit var webView : WebView
    private var binding : FragmentWebViewBinding? = null
    var data = Article()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWebViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.web_view)
        val url = arguments?.getString("url") ?: ""
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)

    }

}