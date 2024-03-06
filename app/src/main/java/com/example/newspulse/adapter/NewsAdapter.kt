package com.example.newspulse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newspulse.data.remote.Article
import com.example.newspulse.databinding.ItemNewsBinding
import com.example.newspulse.utils.formatTimestamp
import com.example.newspulse.utils.loadImage
import com.example.newspulse.utils.toUnixTimestamp

class NewsAdapter(
    private val newsList: List<Article>,
    private val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    inner class NewsVH(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsVH(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        with(holder.binding) {
            val data = newsList[position]
            data.urlToImage.let {
                if (it != null) {
                    imageNews.loadImage(it)
                }
            }
            nameAuthor.text = data.author
            descriptionNews.text = data.description
            textDate.text = data.publishedAt?.toUnixTimestamp()?.formatTimestamp()

            container.setOnClickListener {
                itemClickListener.onClick(data)
            }
        }

    }

    interface ItemClickListener {
        fun onClick(data: Article)
    }
}