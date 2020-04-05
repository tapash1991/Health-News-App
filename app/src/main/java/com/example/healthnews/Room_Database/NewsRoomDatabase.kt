package com.example.healthnews.Room_Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.healthnews.Model.Articles

@Database(entities = [Articles::class], version = 1)
@TypeConverters(Converter::class)
abstract class  NewsRoomDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao?
}