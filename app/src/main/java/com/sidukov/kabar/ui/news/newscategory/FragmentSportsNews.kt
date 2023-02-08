package com.sidukov.kabar.ui.news.newscategory

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import kotlinx.coroutines.flow.collect
import java.io.Serializable
import javax.inject.Inject

class FragmentSportsNews: BaseViewPagerFragment(R.layout.fragment_sports_news), OnItemNewsClicked {

    private lateinit var recyclerViewSportsNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList(), this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sports_news, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val sportsList = Settings.newsAllList.filter { list ->
            list.textCategory == "sports"
        }
        recyclerViewSportsNews = view.findViewById(R.id.recycler_view_news_sports)
        recyclerViewSportsNews.adapter = NewsAdapter(sportsList, this)
        recyclerViewSportsNews.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewSportsNews.addItemDecoration(EmptyDividerItemDecoration())
        newsAdapter.updateList(sportsList)
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