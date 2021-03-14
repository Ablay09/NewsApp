package com.example.newsapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.core.extensions.load
import com.example.newsapp.domain.news.Article
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter(
    private val onItemClicked: (Article) -> Unit
) : ListAdapter<Article, NewsListAdapter.NewsItemViewHolder>(ArticleDiffUtilCallback()) {

    inner class NewsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article) {
            itemView.tvNewsTitle.text = article.title
            itemView.ivNews.load(article.urlToImage)
            itemView.tvSourceTitle.text = article.source.name
            itemView.tvPublishedDate.text = article.publishedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsItemViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: NewsItemViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(getItem(position))
        }
    }
}