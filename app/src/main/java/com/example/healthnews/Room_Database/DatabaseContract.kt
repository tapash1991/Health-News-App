package com.example.healthnews.Room_Database

import android.content.Context
import com.example.healthnews.Model.Articles

interface DatabaseContract {
    interface View {
        fun setRoomDataToViews(articles: List<Articles?>?)
        fun setRefreshing(value: Boolean)
    }

    interface Presenter {
        fun onDestroy()
        fun fetchNewsDataFromDataBase(context: Context?)
        fun saveNewsDataToDataBase(
            articles: List<Articles?>?,
            context: Context?
        )
    }
}