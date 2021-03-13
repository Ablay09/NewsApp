package com.example.newsapp.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R

private const val IMAGE_LOAD_TIMEOUT_MS = 6000

fun ImageView.load(url: String?) {
    Glide.with(context)
        .load(url)
        .timeout(IMAGE_LOAD_TIMEOUT_MS)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(R.drawable.news_placeholder)
        .into(this)
}
