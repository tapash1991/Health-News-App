package com.example.healthnews.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthnews.Model.Articles
import com.example.healthnews.News_Details.DetailNewsActivity
import com.example.healthnews.R
import com.squareup.picasso.Picasso
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter(var context: Context, articles: List<Articles>) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    var articles:MutableList<Articles?>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val a: Articles = articles[position]!!
        val imageUrl: String = a.getUrlToImage()!!
        val url: String = a.getUrl()!!
        Picasso.with(context).load(imageUrl).into(holder.imageView)
        holder.tvTitle.setText(a.getTitle())
        holder.tvSource.setText(a.getSource()!!.getName())
        holder.tvDate.text = "\u2022" + dateTime(a.getPublishedAt())
        holder.cardView.setOnClickListener {
            val intent = Intent(context, DetailNewsActivity::class.java)
            intent.putExtra("title", a.getTitle())
            intent.putExtra("source", a.getSource()!!.getName())
            intent.putExtra("time", dateTime(a.getPublishedAt()))
            intent.putExtra("desc", a.getDescription())
            intent.putExtra("imageUrl", a.getUrlToImage())
            intent.putExtra("url", a.getUrl())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvSource: TextView
        var tvDate: TextView
        var imageView: ImageView
        var cardView: CardView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvSource = itemView.findViewById(R.id.tvSource)
            tvDate = itemView.findViewById(R.id.tvDate)
            imageView = itemView.findViewById(R.id.image)
            cardView = itemView.findViewById(R.id.cardView)
        }
    }

    fun dateTime(t: String?): String? {
        val prettyTime = PrettyTime(Locale(country))
        var time: String? = null
        try {
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH)
            val date = simpleDateFormat.parse(t)
            time = prettyTime.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    val country: String get() {
            val locale = Locale.getDefault()
            val country = locale.country
            return country.toLowerCase()
        }

    fun updateList(list: MutableList<Articles?>) {
        articles = list
        notifyDataSetChanged()
    }

    init {
        this.articles = articles.toMutableList()
    }
}