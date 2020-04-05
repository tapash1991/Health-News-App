package com.example.healthnews.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.healthnews.Room_Database.Converter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity
 class Articles () {

    @PrimaryKey
    var id = 0

    @SerializedName("source")
    @Expose
    @TypeConverters(Converter::class) // add here
    @ColumnInfo(name = "source")
    private var source: Source? = null


    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    private var author: String? = null


    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private var title: String? = null


    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private var description: String? = null


    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    private var url: String? = null


    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    private var urlToImage: String? = null


    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    private var publishedAt: String? = null



    constructor(
        title: String?,
        author: String?,
        description: String?,
        publishedAt: String?,
        source: Source?,
        url: String?,
        urlToImage: String?
    ) : this() {
        this.title = title
        this.author = author
        this.description = description
        this.publishedAt = publishedAt
        this.source = source
        this.url = url
        this.urlToImage = urlToImage
    }

    fun getUid(): Int {
        return id
    }

    fun setUid(uid: Int) {
        this.id = uid
    }


    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getSource(): Source? {
        return source
    }

    fun setSource(source: Source?) {
        this.source = source
    }

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String?) {
        this.author = author
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getUrlToImage(): String? {
        return urlToImage
    }

    fun setUrlToImage(urlToImage: String?) {
        this.urlToImage = urlToImage
    }

    fun getPublishedAt(): String? {
        return publishedAt
    }

    fun setPublishedAt(publishedAt: String?) {
        this.publishedAt = publishedAt
    }
}