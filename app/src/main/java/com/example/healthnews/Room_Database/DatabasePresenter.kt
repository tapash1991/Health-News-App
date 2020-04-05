package com.example.healthnews.Room_Database

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.healthnews.Model.Articles
import java.util.*

class DatabasePresenter(var dataBaseView: DatabaseContract.View?): DatabaseContract.Presenter {



    var sentarticlesList: MutableList<Articles?>? = ArrayList<Articles?>()


    override fun onDestroy() {
        dataBaseView = null
    }

    override fun fetchNewsDataFromDataBase(context: Context?) {
        val activity = context as Activity
        fetchNewsFromRoom(context, activity)
    }

    override fun saveNewsDataToDataBase(articles: List<Articles?>?, context: Context?) {
        if (articles != null && context != null) {
            saveNewsToRoomDatabase(articles as List<Articles>, context)
        }

    }

    private fun saveNewsToRoomDatabase(sentarticlesList: List<Articles>, context: Context) {
        class SaveTask : AsyncTask<Void?, Void?, Void?>() {
             override fun doInBackground(vararg params: Void?): Void? {
                 //Delete Previous Table Data To insert New Data
                 val db = Room.databaseBuilder(
                     context,
                     NewsRoomDatabase::class.java, "database-name"
                 ).build()
                 db.articleDao()!!.DeleteTable()

                //creating a task

                for (i in sentarticlesList.indices) {
                    val article = Articles()
                    article.setUid(i)
                    article.setTitle(sentarticlesList[i].getTitle())
                    article.setDescription(sentarticlesList[i].getDescription())
                    article.setSource(sentarticlesList[i].getSource())
                    article.setPublishedAt(sentarticlesList[i].getPublishedAt())
                    article.setUrl(sentarticlesList[i].getUrl())
                    article.setUrlToImage(sentarticlesList[i].getUrlToImage())

                    db.articleDao()!!.insert(article)
                }
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
            }


        }

        val st = SaveTask()
        st.execute()
    }


    private fun fetchNewsFromRoom(context: Context, activity: Activity) {
        val thread = Thread(Runnable {
            val db = Room.databaseBuilder(
                context,
                NewsRoomDatabase::class.java, "database-name"
            ).build()
            val roomArticleList: List<Articles>  = db.articleDao()!!.getall() as List<Articles>
            sentarticlesList!!.clear()
            for (articles in roomArticleList) {
                val repo = Articles(
                    articles.getTitle(), articles.getAuthor(),
                    articles.getDescription(),
                    articles.getPublishedAt(),
                    articles.getSource(),
                    articles.getUrl(),
                    articles.getUrlToImage()
                )
                sentarticlesList!!.add(repo)
            }
            // refreshing recycler view
            activity.runOnUiThread {
                Log.i("@data",sentarticlesList.toString())
                    dataBaseView!!.setRoomDataToViews(sentarticlesList)
                    dataBaseView!!.setRefreshing(false)
            }
        })
        thread.start()
    }




}