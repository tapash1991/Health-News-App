package com.example.healthnews.News_List

import com.example.healthnews.Model.Headlines

interface NewsDetailsContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(headlines: Headlines?)
            fun onFailure(t: Throwable?)
        }

        fun getNewsDetailsList(
            onFinishedListener: OnFinishedListener?,
            country: String?,
            apiKey: String?,
            category: String?,
            pageid: String?
        )
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToViews(movie: Headlines?)
        fun setRefreshing(value: Boolean)
        fun onResponseFailure(throwable: Throwable?)
    }

    interface Presenter {
        fun onDestroy()
        fun requestNewsData(
            country: String?,
            apiKey: String?,
            category: String?,
            pageid: String?
        )
    }
}