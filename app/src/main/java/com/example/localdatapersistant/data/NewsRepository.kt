package com.example.localdatapersistant.data

import android.util.Log
import androidx.core.os.BuildCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.localdatapersistant.BuildConfig
import com.example.localdatapersistant.data.local.entity.NewsEntity
import com.example.localdatapersistant.data.local.room.NewsDao
import com.example.localdatapersistant.data.remote.response.NewsResponse
import com.example.localdatapersistant.data.remote.retrofit.ApiService
import com.example.localdatapersistant.utils.AppExecutors
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
){
  private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadLineNews():LiveData<Result<List<NewsEntity>>>{
        result.value = Result.Loading
        val client = apiService.getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute{
                        articles?.forEach{ article ->
                            val isBookmarked = newsDao.isNewBookMarked(article?.title.toString())
                            val news = NewsEntity(
                                article?.title.toString(),
                                article?.publishedAt.toString(),
                                article?.urlToImage,
                                article?.url,
                                isBookmarked
                            )
                            newList.add(news)
                            Log.d("NeswRepository", "Cek Datanya $news")
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        val localData = newsDao.getNews()
        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }
    companion object {
        @Volatile
        private var instance:NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}