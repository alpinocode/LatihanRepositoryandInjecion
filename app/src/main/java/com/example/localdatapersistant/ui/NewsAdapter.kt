package com.example.localdatapersistant.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.localdatapersistant.R
import com.example.localdatapersistant.data.local.entity.NewsEntity
import com.example.localdatapersistant.databinding.ItemNewsBinding
import com.example.localdatapersistant.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit): ListAdapter<NewsEntity, NewsAdapter.MyViewHolder>(DIFF_CALBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_bookmark_24))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_bookmark_border_24))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }
    class MyViewHolder(val binding:ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(news:NewsEntity) {
                binding.tvItemTitle.text = news.title
                binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(binding.imgPoster)

                itemView.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(news.url)
                    itemView.context.startActivity(intent)
                }
            }
    }



    companion object {
        val DIFF_CALBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

}