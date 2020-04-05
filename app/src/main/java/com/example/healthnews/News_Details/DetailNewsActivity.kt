package com.example.healthnews.News_Details

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthnews.R
import com.squareup.picasso.Picasso

class DetailNewsActivity : AppCompatActivity() {
    var tvTitle: TextView? = null
    var tvSource: TextView? = null
    var tvTime: TextView? = null
    var tvDesc: TextView? = null
    var imageView: ImageView? = null
    var webView: WebView? = null
    var loader: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_list_detailed)
        tvTitle = findViewById(R.id.tvTitle)
        tvSource = findViewById(R.id.tvSource)
        tvTime = findViewById(R.id.tvDate)
        tvDesc = findViewById(R.id.tvDesc)
        imageView = findViewById(R.id.imageView)
        webView = findViewById(R.id.webView)
        loader = findViewById(R.id.webViewLoader)
        loader!!.setVisibility(View.VISIBLE)
        val intent = intent
        val title = intent.getStringExtra("title")
        val source = intent.getStringExtra("source")
        val time = intent.getStringExtra("time")
        val desc = intent.getStringExtra("desc")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")
        tvTitle!!.setText(title)
        tvSource!!.setText(source)
        tvTime!!.setText(time)
        tvDesc!!.setText(desc)
        Picasso.with(this@DetailNewsActivity).load(imageUrl).into(imageView)
        webView!!.getSettings().domStorageEnabled = true
        webView!!.getSettings().javaScriptEnabled = true
        webView!!.getSettings().loadsImagesAutomatically = true
        webView!!.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        webView!!.setWebViewClient(WebViewClient())
        webView!!.loadUrl(url)
        if (webView!!.isShown()) {
            loader!!.setVisibility(View.INVISIBLE)
        }
    }
}
