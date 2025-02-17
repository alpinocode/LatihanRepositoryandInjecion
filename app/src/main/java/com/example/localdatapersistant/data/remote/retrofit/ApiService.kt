package com.example.localdatapersistant.data.remote.retrofit

import com.example.localdatapersistant.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything?q=tesla&from=2025-01-17&sortBy=publishedAt")
    fun getNews(
        @Query("apiKey") apiKey:String): Call<NewsResponse>
}