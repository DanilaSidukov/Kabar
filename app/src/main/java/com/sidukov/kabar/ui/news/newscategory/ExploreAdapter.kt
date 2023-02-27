package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter.Companion.difference
import com.squareup.picasso.Picasso

class ExploreAdapter(
    private var exploreList: List<EntityNews>
): RecyclerView.Adapter<ExploreAdapter.PopularTopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_topic, parent, false)
        return PopularTopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularTopicViewHolder, position: Int) {
        Picasso.get().load(exploreList[position].newsImage).into(holder.newsImagePopularTopic)
        holder.categoryPopularTopic.text = exploreList[position].category
        holder.titlePopularTopic.text = exploreList[position].title
        holder.authorImagePopularTopic.setImageResource(R.drawable.ic_pencil_news)
        holder.authorPopularTopic.text = exploreList[position].author
        holder.datePopularTopic.text = exploreList[position].date.difference()
    }

    override fun getItemCount() = exploreList.size

    class PopularTopicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val newsImagePopularTopic: ImageView = itemView.findViewById(R.id.image_popular_topic)
        val categoryPopularTopic: TextView = itemView.findViewById(R.id.text_category_popular_topic)
        val titlePopularTopic: TextView = itemView.findViewById(R.id.text_title_popular_topic)
        val authorImagePopularTopic: ImageView = itemView.findViewById(R.id.image_author_popular_topic)
        val authorPopularTopic: TextView = itemView.findViewById(R.id.text_author_popular_topic)
        val datePopularTopic: TextView = itemView.findViewById(R.id.date_popular_topic)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<EntityNews>){
        exploreList = list
        notifyDataSetChanged()
    }

}