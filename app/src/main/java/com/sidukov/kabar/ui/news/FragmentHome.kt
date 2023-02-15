package com.sidukov.kabar.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sidukov.kabar.R
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.newscategory.NewsViewModel
import javax.inject.Inject

class FragmentHome : BaseViewPagerFragment(R.layout.fragment_home) {

    private lateinit var tableLayoutNews: ComposeView

    companion object {
        val tempList = listOf<EntityNews>(
            EntityNews("news",
                "all",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "sports",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "entertainment",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "technology",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "science",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "food",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "haha",
                "something",
                null,
                "it's me",
                null,
                323232121212),
            EntityNews("news",
                "sports",
                "something",
                null,
                "it's me",
                null,
                323232121212),
        )
    }


    @Inject
    lateinit var newsViewModelFactory: ViewModelProvider.Factory
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewsApplication.appComponent.inject(this)
        newsViewModel = injectViewModel(newsViewModelFactory)

        tableLayoutNews = view.findViewById(R.id.table_layout_news)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            newsViewModel.requestNews()
        }

        tableLayoutNews.setContent {
            MaterialTheme {

                val newsList by newsViewModel.newsData.collectAsState(emptyList())
                val isDataLoaded by newsViewModel.isDataLoaded.collectAsState()

                NewsViewPager(newsList, isDataLoaded)
            }
        }
    }
}