package com.sidukov.kabar.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.common.EmptyDividerItemDecoration
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.newscategory.ActivityArticleNews
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter
import com.sidukov.kabar.ui.news.newscategory.OnItemNewsClicked
import java.io.Serializable
import javax.inject.Inject

class FragmentBookmark : BaseViewPagerFragment(R.layout.fragment_bookmark), OnItemNewsClicked {

    private lateinit var bookmarkRecyclerView: RecyclerView
    private lateinit var buttonSettings: ImageButton
    private var newsAdapter = NewsAdapter(emptyList(), this)

    @Inject
    lateinit var newsViewModelFactory: ViewModelProvider.Factory
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewsApplication.appComponent.inject(this)
        newsViewModel = injectViewModel(newsViewModelFactory)

        bookmarkRecyclerView = view.findViewById(R.id.recycler_view_news_bookmark)
        bookmarkRecyclerView.adapter = newsAdapter
        bookmarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookmarkRecyclerView.addItemDecoration(EmptyDividerItemDecoration())

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            newsViewModel.bookmarkData.collect { list ->
                newsAdapter.updateList(list)
            }
        }

        buttonSettings = view.findViewById(R.id.button_settings)
        buttonSettings.setOnClickListener { }

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