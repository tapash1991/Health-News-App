package com.example.healthnews.Room_Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthnews.Model.Articles

@Dao
interface ArticleDao {
    @Query("SELECT * FROM Articles")
    fun getall(): List<Articles?>?

    @Insert
    fun insert(recipe: Articles?)

    @Query("DELETE FROM Articles")
    fun DeleteTable()
}