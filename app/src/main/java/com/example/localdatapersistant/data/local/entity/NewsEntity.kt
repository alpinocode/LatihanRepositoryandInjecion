package com.example.localdatapersistant.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity (
    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title:String,

    @ColumnInfo(name = "publishedAt")
    val publishedAt:String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage:String? = null,

    @ColumnInfo(name = "url")
    val url:String? = null,

    @ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)