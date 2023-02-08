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
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import java.io.Serializable
import javax.inject.Inject

class FragmentTechnologyNews: BaseViewPagerFragment(R.layout.fragment_technology_news), OnItemNewsClicked {

    private lateinit var recyclerViewTechnologyNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList(), this)

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

        val technologyList = Settings.newsAllList.filter { list ->
            list.textCategory == "technology"
        }
        recyclerViewTechnologyNews = view.findViewById(R.id.recycler_view_news_technology)
        recyclerViewTechnologyNews.adapter = NewsAdapter(technologyList, this)
        recyclerViewTechnologyNews.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewTechnologyNews.addItemDecoration(EmptyDividerItemDecoration())
        newsAdapter.updateList(technologyList)
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