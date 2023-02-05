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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentTechnologyNews: BaseViewPagerFragment(R.layout.fragment_technology_news) {

    private lateinit var recyclerViewTechnologyNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList())
    private val newsViewModel: NewsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_technology_news, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewTechnologyNews = view.findViewById(R.id.recycler_view_news_technology)
        recyclerViewTechnologyNews.adapter = NewsAdapter(emptyList())
        recyclerViewTechnologyNews.addItemDecoration(EmptyDividerItemDecoration())

        val technologyList = mutableListOf<NewsItem>().apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                newsViewModel.newsData.collect{ list ->
                    list.filter { it.textCategory == "technology" }
                }
            }
        }
        newsAdapter.updateList(technologyList)
    }

}