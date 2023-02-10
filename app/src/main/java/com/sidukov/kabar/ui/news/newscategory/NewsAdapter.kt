package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository.Companion.DATE_PATTERN
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter.Companion.difference
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class NewsAdapter(
    private var newsList: List<EntityNews>,
    private val listener: OnItemNewsClicked,
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    companion object {
        fun Long?.difference(): String? {
            if (this == null) return null

            val newsDate = Date(this)

            val now = Calendar.getInstance().time
            val difference = now.time - newsDate.time

            val seconds = (difference / 1000).toInt()
            val minutes =(seconds / 60)
            val hours = (minutes / 60)
            val days = (hours / 24)

            return if (days != 0) "$days d ago"
            else if (hours != 0) "$hours h ago"
            else if (minutes != 0) "$minutes m ago"
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

        if (newsList[position].newsImage != null || !newsList[position].newsImage.isNullOrBlank()) Picasso.get().load(newsList[position].newsImage)
            .into(holder.newsImage)
        else holder.newsImage.setImageResource(R.drawable.ic_news)
        holder.category.text = newsList[position].category
        holder.title.text = newsList[position].title
        holder.authorImage.setImageResource(R.drawable.ic_pencil_news)
        holder.author.text = newsList[position].author
        holder.date.text = newsList[position].date.difference().toString()
    }

    override fun getItemCount() = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<EntityNews>) {
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
    fun onItemNewsClicked(itemNews: EntityNews)
}