package com.sidukov.kabar.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.newscategory.EmptyDividerItemDecoration
import com.sidukov.kabar.ui.news.newscategory.ExploreAdapter

class FragmentExplore: BaseViewPagerFragment(R.layout.fragment_explore) {

    private lateinit var popularTopicRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularTopicRecyclerView = view.findViewById(R.id.popular_topic_recycler_view_explore)
        popularTopicRecyclerView.adapter = ExploreAdapter(emptyList())
        popularTopicRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        popularTopicRecyclerView.addItemDecoration(EmptyDividerItemDecoration())

    }

}