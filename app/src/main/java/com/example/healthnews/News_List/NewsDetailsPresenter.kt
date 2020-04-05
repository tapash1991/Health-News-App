package com.example.healthnews.News_List

import com.example.healthnews.Model.Headlines

public class NewsDetailsPresenter : NewsDetailsContract.Presenter , NewsDetailsContract.Model.OnFinishedListener  {


    private var newsDetailView: NewsDetailsContract.View? = null
    private var newsDetailsModel: NewsDetailsContract.Model? = null

   constructor(newsDetailView: NewsDetailsContract.View?) {
        this.newsDetailView = newsDetailView
        this.newsDetailsModel = NewsDetailsModel()
    }
    override fun onFinished(headlines: Headlines?) {
        if (newsDetailView != null) {
            newsDetailView!!.hideProgress()
            newsDetailView!!.setRefreshing(false)
        }
        newsDetailView!!.setDataToViews(headlines)
    }

    override fun onFailure(t: Throwable?) {
        if (newsDetailView != null) {
            newsDetailView!!.hideProgress()
            newsDetailView!!.setRefreshing(false)
        }
        newsDetailView!!.onResponseFailure(t)
    }

    override fun onDestroy() {
        newsDetailView = null
    }

    override fun requestNewsData(
        country: String?,
        apiKey: String?,
        category: String?,
        pageid: String?
    ) {
        if (newsDetailView != null) {
            newsDetailView!!.showProgress()
            newsDetailView!!.setRefreshing(true)
        }
        newsDetailsModel!!.getNewsDetailsList(this, country, apiKey, category, pageid)
    }
}