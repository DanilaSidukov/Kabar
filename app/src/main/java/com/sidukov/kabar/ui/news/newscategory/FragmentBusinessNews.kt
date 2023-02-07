package com.sidukov.kabar.ui.news.newscategory

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment

class FragmentBusinessNews : BaseViewPagerFragment(R.layout.fragment_business_news), OnItemNewsClicked {

    private lateinit var recyclerViewBusinessNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList(), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_business_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val businessList = Settings.newsAllList.filter { list ->
            list.textCategory == "business"
        }
        println("filter list = $businessList")
        recyclerViewBusinessNews = view.findViewById(R.id.recycler_view_news_business)
        recyclerViewBusinessNews.adapter = NewsAdapter(businessList, this)
        recyclerViewBusinessNews.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewBusinessNews.addItemDecoration(EmptyDividerItemDecoration())
        newsAdapter.updateList(businessList)
    }

    override fun onItemNewsClicked(itemNews: NewsItem) {

    }
}