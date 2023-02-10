package com.sidukov.kabar.ui.news.newscategory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import java.io.Serializable
import javax.inject.Inject

class FragmentPoliticsNews: BaseViewPagerFragment(R.layout.fragment_politics_news), OnItemNewsClicked {

    private lateinit var recyclerPoliticsNews: RecyclerView
    private var newsAdapter = NewsAdapter(emptyList(), this)
    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_politics_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewsApplication.appComponent.inject(this)

        recyclerPoliticsNews = view.findViewById(R.id.recycler_view_news_politics)
        recyclerPoliticsNews.layoutManager = LinearLayoutManager(requireContext())
        recyclerPoliticsNews.addItemDecoration(EmptyDividerItemDecoration())
        recyclerPoliticsNews.adapter = newsAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val politicsList = newsRepository.getAllNews().filter { it.category == "politics" }
            newsAdapter.updateList(politicsList)
        }
    }

    override fun onItemNewsClicked(itemNews: EntityNews) {
        parentFragment?.activity?.let { generalActivity ->
            startActivity(
                Intent(generalActivity, ActivityArticleNews::class.java).also {
                    it.putExtra("item_news", itemNews as Serializable)
                }
            )
        }
    }

}