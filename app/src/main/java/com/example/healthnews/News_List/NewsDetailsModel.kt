package com.example.healthnews.News_List

import android.content.ContentValues
import android.util.Log
import com.example.healthnews.Model.Headlines
import com.example.healthnews.Network.ApiInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class NewsDetailsModel : NewsDetailsContract.Model {
    override fun getNewsDetailsList(
        onFinishedListener: NewsDetailsContract.Model.OnFinishedListener?,
        country: String?,
        apiKey: String?,
        category: String?,
        pageid: String?
    ) {

        val apiService = ApiInterface.create()


        val call: Call<Headlines> =
            apiService.getHeadlines(country!!, apiKey!!, category!!, pageid!!)

        call.enqueue(object : Callback<Headlines> {
            override fun onResponse(
                call: Call<Headlines>,
                response: Response<Headlines>
            ) {
                if (response.isSuccessful && response.body()!!.getArticles() != null) { //   swipeRefreshLayout.setRefreshing(false);
                    val headlines = response.body()
                    Log.d(
                        ContentValues.TAG,
                        "News data received: " + headlines.toString()
                    )
                    onFinishedListener!!.onFinished(headlines)
                }
            }

            override fun onFailure(
                call: Call<Headlines>,
                t: Throwable
            ) { //  swipeRefreshLayout.setRefreshing(false);
                Log.d(ContentValues.TAG, "News data received: " + t.message.toString())
            }
        })
    }

}