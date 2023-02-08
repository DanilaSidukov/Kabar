package com.sidukov.kabar.ui.news.newscategory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import java.io.Serializable
import javax.inject.Inject

class FragmentAllNews : BaseViewPagerFragment(R.layout.fragment_all_news), OnItemNewsClicked {

    private lateinit var recyclerViewAllNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_all_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewsApplication.appComponent.inject(this)
        newsViewModel= injectViewModel(viewModelFactory)
//            parentFragment?.activity?.let { ViewModelProvider(it.viewModelStore, viewModelFactory)[NewsViewModel::class.java] }!!

        recyclerViewAllNews = view.findViewById(R.id.recycler_view_news_all)
        recyclerViewAllNews.adapter = newsAdapter
        recyclerViewAllNews.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAllNews.addItemDecoration(EmptyDividerItemDecoration())

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            newsViewModel.newsData.collect {
                println("all news = $it")
                newsAdapter.updateList(it)
            }
        }
    }

    override fun onItemNewsClicked(itemNews: NewsItem) {
        parentFragment?.activity?.let { generalActivity ->
            startActivity(
                Intent(generalActivity, ActivityArticleNews::class.java).also {
                    it.putExtra("item_news", itemNews as Serializable)
                }
            )
        }
    }

}