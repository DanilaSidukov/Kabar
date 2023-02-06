package com.sidukov.kabar.ui.news.newscategory

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment

class FragmentEntertainmentNews : BaseViewPagerFragment(R.layout.fragment_entertainment_news) {

    private lateinit var recyclerViewEntertainmentNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_entertainment_news, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewEntertainmentNews = view.findViewById(R.id.recycler_view_news_entertainment)
        recyclerViewEntertainmentNews.adapter = NewsAdapter(emptyList())
        recyclerViewEntertainmentNews.addItemDecoration(EmptyDividerItemDecoration())

//        val entertainmentList = mutableListOf<NewsItem>().apply {
//            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//                newsViewModel.newsData.collect { list ->
//                    list.filter {
//                        it.textCategory == "entertainment"
//                    }
//                }
//            }
//        }
//        newsAdapter.updateList(entertainmentList)
    }

}