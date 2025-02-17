package com.example.localdatapersistant.ui

import androidx.lifecycle.ViewModel
import com.example.localdatapersistant.data.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadLineNews()
}