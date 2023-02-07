package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.domain.NewsItem
import com.squareup.picasso.Picasso
import java.util.*

class NewsAdapter(
    private var newsList: List<NewsItem>,
    private val listener: OnItemNewsClicked,
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    companion object {
        fun Date?.difference(): String {
            if (this == null) return ""

            val now = Calendar.getInstance().time
            val difference = Date(now.time - this.time)

            println("DATE = $difference")

            return if (difference.date != 0) "${difference.date}d ago"
            else if (difference.date == 0 && difference.hours != 0) "${difference.hours}h ago"
            else if (difference.hours == 0 && difference.minutes != 0) "${difference.minutes}m ago"
            else "now"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemNewsClicked(newsList[position])
        }

        if (newsList[position].newsImage != null) Picasso.get().load(newsList[position].newsImage)
            .into(holder.newsImage)
        else holder.newsImage.setImageResource(R.drawable.ic_news)
        holder.category.text = newsList[position].textCategory
        holder.title.text = newsList[position].titleText
        holder.authorImage.setImageResource(newsList[position].authorImage
            ?: R.drawable.ic_pencil_news)
        holder.author.text = newsList[position].author
        holder.date.text = newsList[position].date?.difference()
    }

    override fun getItemCount() = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<NewsItem>) {
        newsList = list
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.image_news)
        val category: TextView = itemView.findViewById(R.id.text_category)
        val title: TextView = itemView.findViewById(R.id.text_description)
        val authorImage: ImageView = itemView.findViewById(R.id.image_author)
        val author: TextView = itemView.findViewById(R.id.text_author)
        val date: TextView = itemView.findViewById(R.id.text_date)
    }

}

interface OnItemNewsClicked {
    fun onItemNewsClicked(itemNews: NewsItem)
}