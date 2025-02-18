package com.example.localdatapersistant.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.localdatapersistant.data.NewsRepository
import com.example.localdatapersistant.data.local.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadLineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, true)
    }

    fun deleteNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news,false)
    }
}