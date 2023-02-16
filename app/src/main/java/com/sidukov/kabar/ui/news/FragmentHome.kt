package com.sidukov.kabar.ui.news

import android.content.Intent
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
import com.sidukov.kabar.ui.news.newscategory.ActivityArticleNews
import java.io.Serializable
import javax.inject.Inject

class FragmentHome : BaseViewPagerFragment(R.layout.fragment_home) {

    private lateinit var tableLayoutNews: ComposeView

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

        var newsList = emptyList<EntityNews>()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            newsViewModel.requestNews()
            newsViewModel.newsData.collect{
                newsList = it
            }
        }

        tableLayoutNews.setContent {
            MaterialTheme {

                val listOfNews by newsViewModel.newsData.collectAsState(newsList)
                val isDataLoaded by newsViewModel.isDataLoaded.collectAsState()

                NewsViewPager(listOfNews, isDataLoaded) { entityNews ->
                    startActivity(
                        Intent(context, ActivityArticleNews::class.java).also {
                            it.putExtra("item_news", entityNews as Serializable)
                        }
                    )
                }
            }
        }
    }
}