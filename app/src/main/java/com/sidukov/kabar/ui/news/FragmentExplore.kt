package com.sidukov.kabar.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.newscategory.*
import java.io.Serializable
import javax.inject.Inject

class FragmentExplore: BaseViewPagerFragment(R.layout.fragment_explore), OnItemNewsClicked{

    private lateinit var popularTopicRecyclerView: RecyclerView
    private val newsAdapter = NewsAdapter(emptyList(), this)
    private lateinit var textSeeAll: TextView

    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewsApplication.appComponent.inject(this)

        popularTopicRecyclerView = view.findViewById(R.id.popular_topic_recycler_view_explore)
        popularTopicRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        popularTopicRecyclerView.addItemDecoration(EmptyDividerItemDecoration())

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val topicNews = newsRepository.getNews().filter { it.category == "top" }
            newsAdapter.updateList(topicNews)
        }

        textSeeAll = view.findViewById(R.id.text_see_all)
        textSeeAll.setOnClickListener {
            (activity as? ActivityGeneral)?.openAllNewsFragment()
        }

    }

    override fun onItemNewsClicked(itemNews: EntityNews) {
        activity?.let { generalActivity ->
            startActivity(
                Intent(generalActivity, ActivityArticleNews::class.java).also {
                    it.putExtra("item_news", itemNews as Serializable)
                }
            )
        }
    }

}