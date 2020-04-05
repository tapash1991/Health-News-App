package com.example.healthnews.News_List

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.healthnews.Adapter.NewsListAdapter
import com.example.healthnews.Model.Articles
import com.example.healthnews.Model.Headlines
import com.example.healthnews.R
import com.example.healthnews.Room_Database.DatabaseContract
import com.example.healthnews.Room_Database.DatabasePresenter
import com.example.healthnews.Utils.NewsUtil
import java.util.*

class NewsListActivity : AppCompatActivity(), NewsDetailsContract.View ,DatabaseContract.View {

    var recyclerView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var etQuery: EditText? = null
    var btnSearch: Button? = null
    var adapter: NewsListAdapter? = null
    var gotarticlesList: MutableList<Articles?>? = ArrayList<Articles?>()
    var sentarticlesList: MutableList<Articles?>? = ArrayList<Articles?>()
    private var newsDetailsPresenter: NewsDetailsPresenter? = null
    private var databasePresenter: DatabasePresenter? = null
    var manager: LinearLayoutManager? = null
    var isScrolling = false
    var currentItems = 0
    var totalItems:Int = 0
    var scrolledItems:Int = 0

    var pageCount = 0

    var mcontext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initListner()
        mcontext =this
        manager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = manager
        newsDetailsPresenter = NewsDetailsPresenter(this)
        databasePresenter = DatabasePresenter(this)
        pageCount++
        if (NewsUtil.isConnectedToInternet(mcontext!!)) {
            newsDetailsPresenter!!.requestNewsData(
                (mcontext!!).getString(R.string.country),
                NewsUtil.API_KEY,
                (mcontext!!).getString(R.string.category),
                pageCount.toString()
            )
        } else {
            databasePresenter!!.fetchNewsDataFromDataBase(mcontext)
            NewsUtil.showAlertDialog(mcontext as NewsListActivity)
        }
    }


    /**
     * Initializing UI Listners
     */
    private fun initListner() {
        swipeRefreshLayout!!.setOnRefreshListener {
            // on Refresh we have to call the new data so clear all the previous data
            pageCount = 0
            gotarticlesList!!.clear()
            sentarticlesList!!.clear()
            if (NewsUtil.isConnectedToInternet(mcontext!!)) {
                newsDetailsPresenter!!.requestNewsData(
                    (mcontext!!).getString(R.string.country),
                    NewsUtil.API_KEY,
                    (mcontext!!).getString(R.string.category),
                    "1"
                )
            } else {
                databasePresenter!!.fetchNewsDataFromDataBase(mcontext)
                NewsUtil.showAlertDialog(mcontext as NewsListActivity)
                setRefreshing(false);
            }
        }
        btnSearch!!.setOnClickListener {
            if (NewsUtil.isConnectedToInternet(mcontext!!)) {
                if (etQuery!!.text.toString() != "") {
                    swipeRefreshLayout!!.setOnRefreshListener { filter(etQuery!!.text.toString()) }
                    filter(etQuery!!.text.toString())
                } else {
                    swipeRefreshLayout!!.setOnRefreshListener { newsDetailsPresenter!!.requestNewsData(mcontext!!.getString(R.string.country), NewsUtil.API_KEY, getString(R.string.category), "1")
                    }
                    newsDetailsPresenter!!.requestNewsData(
                        mcontext!!.getString(R.string.country),
                        NewsUtil.API_KEY,
                        getString(R.string.category),
                        "1"
                    )
                }
            } else {
                NewsUtil.showAlertDialog(mcontext!!)
            }
        }
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) setRefreshing(
                    false
                )
                isScrolling = true
                val activity = mcontext as Activity?
                NewsUtil.hideKeyboard(activity!!)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager!!.childCount
                totalItems = manager!!.itemCount
                scrolledItems = manager!!.findFirstVisibleItemPosition()
                if (isScrolling && currentItems + scrolledItems == totalItems) {
                    isScrolling = false
                    //Fetch the new data with new Page No when user completes viewing all data from the first page
                    pageCount++
                    newsDetailsPresenter!!.requestNewsData(
                        mcontext!!.getString(R.string.country),
                        NewsUtil.API_KEY,
                        getString(R.string.category),
                        pageCount.toString()
                    )
                }
            }
        })
        etQuery!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // filter your list from your input
                filter(s.toString())
            }
        })
    }

    private fun filter(toString: String) {
        val filteredArticleList: MutableList<Articles?> = ArrayList<Articles?>()
        for (d in sentarticlesList!!) {
            if (d!!.getTitle()!!.toLowerCase().contains(toString.toLowerCase())) {
                filteredArticleList.add(d)
            }
        }
        setRefreshing(false)
        //update recyclerview
        adapter!!.updateList(filteredArticleList)
    }

    /**
     * Initializing UI components
     */
    private fun initUI() {
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        recyclerView = findViewById(R.id.recyclerView)
        etQuery = findViewById(R.id.etQuery)
        btnSearch = findViewById(R.id.btnSearch)
    }

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun setDataToViews(headlines: Headlines?) {
        if (headlines != null) {
            if (gotarticlesList != null) gotarticlesList!!.clear()
            gotarticlesList = headlines.getArticles() as MutableList<Articles?>?
            //Retrive Each Articals And added to new list which to be send to recyclerView
            if (gotarticlesList != null) {
                for (i in gotarticlesList!!.indices) {
                    val articles1: Articles? = gotarticlesList!![i]
                    sentarticlesList!!.add(articles1)
                }
            }
            if (pageCount <= 1 && sentarticlesList != null) setRecyclerViewAdapter(sentarticlesList!!) else adapter!!.notifyDataSetChanged()
            // Save all the news data to Room Database to view Later on when internet connection Lost
            if (etQuery!!.text.toString().isEmpty() && etQuery!!.text.toString().equals("", ignoreCase = true))
                databasePresenter!!.saveNewsDataToDataBase(sentarticlesList, mcontext)
        }
    }



    override fun setRoomDataToViews(articles: List<Articles?>?) {
              Log.i("@dataInActivity",articles.toString())
           // sentarticlesList!!.clear()
            sentarticlesList = articles as MutableList<Articles?>? // For Filtering When No internet
             Log.i("@dataInActivity",sentarticlesList.toString())
             Log.i("@dataInActivity",articles.toString())
           adapter = NewsListAdapter(this@NewsListActivity, sentarticlesList as List<Articles>)
            recyclerView!!.adapter = adapter

    }

    override fun setRefreshing(value: Boolean) {
        swipeRefreshLayout!!.isRefreshing = value
    }


    override fun onResponseFailure(throwable: Throwable?) {}

    override fun onDestroy() {
        super.onDestroy()
        newsDetailsPresenter!!.onDestroy()
        databasePresenter!!.onDestroy()
    }


    private fun setRecyclerViewAdapter(articles: List<Articles?>) {
        adapter = NewsListAdapter(this@NewsListActivity, articles as List<Articles>)
        recyclerView!!.adapter = adapter
    }


}
