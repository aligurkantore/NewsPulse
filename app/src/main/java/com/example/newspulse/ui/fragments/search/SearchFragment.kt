package com.example.newspulse.ui.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newspulse.R
import com.example.newspulse.adapter.NewsAdapter
import com.example.newspulse.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var binding : FragmentSearchBinding? = null
    private var adapter : NewsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}