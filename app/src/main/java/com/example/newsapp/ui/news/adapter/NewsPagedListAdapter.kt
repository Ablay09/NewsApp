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

class NewsPagedListAdapter(
    private val onItemClicked: (Article) -> Unit
) : PagedListAdapter<Article, NewsPagedListAdapter.NewsItemViewHolder>(ArticleDiffUtilCallback()) {

    inner class NewsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article, payloads: List<Any> = emptyList()) {
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
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position)?.let { article ->
            holder.bind(article, payloads.filterNotNull())
            holder.itemView.setOnClickListener {
                onItemClicked.invoke(article)
            }
        }
    }

    override fun onBindViewHolder(
        holder: NewsItemViewHolder,
        position: Int
    ) {
        getItem(position)?.let { article ->
            holder.bind(article)
            holder.itemView.setOnClickListener {
                onItemClicked.invoke(article)
            }
        }
    }

}

class ArticleDiffUtilCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.source.id == newItem.source.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}