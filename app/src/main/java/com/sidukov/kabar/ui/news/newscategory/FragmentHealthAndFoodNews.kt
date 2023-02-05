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

class FragmentHealthAndFoodNews: BaseViewPagerFragment(R.layout.fagment_health_news) {

    private lateinit var recyclerViewHealthNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList())
    private val newsViewModel: NewsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fagment_health_news, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewHealthNews = view.findViewById(R.id.recycler_view_news_health)
        recyclerViewHealthNews.adapter = NewsAdapter(emptyList())
        recyclerViewHealthNews.addItemDecoration(EmptyDividerItemDecoration())

        val healthAndFoodList = mutableListOf<NewsItem>().apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                newsViewModel.newsData.collect{ list ->
                    list.filter {
                        it.textCategory == "health" || it.textCategory == "food"
                    }
                }
            }
        }

        newsAdapter.updateList(healthAndFoodList)

    }

}