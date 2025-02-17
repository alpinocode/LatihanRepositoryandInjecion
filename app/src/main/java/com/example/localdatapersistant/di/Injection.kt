package com.example.localdatapersistant.di

import android.content.Context
import com.example.localdatapersistant.data.NewsRepository
import com.example.localdatapersistant.data.local.room.NewsDatabase
import com.example.localdatapersistant.data.remote.retrofit.ApiConfig
import com.example.localdatapersistant.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context):NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()

        return NewsRepository.getInstance(apiService,dao,appExecutors)
    }
}