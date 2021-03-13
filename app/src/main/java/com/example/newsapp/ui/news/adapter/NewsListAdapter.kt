package com.example.newsapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.core.extensions.load
import com.example.newsapp.domain.news.Article
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter : PagedListAdapter<Article, NewsListAdapter.NewsItemViewHolder>(ArticleDiffUtilCallback()) {

    inner class NewsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article, payloads: List<Any> = emptyList()) {
            itemView.tvNewsTitle.text = article.title
            itemView.ivNews.load(article.urlToImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NewsItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position)?.let { holder.bind(it, payloads.filterNotNull()) }
    }

    override fun onBindViewHolder(
        holder: NewsItemViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class ArticleDiffUtilCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.source.id == newItem.source.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Article, newItem: Article): Any? {
        return super.getChangePayload(oldItem, newItem)
    }

}