package com.sidukov.kabar.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.newscategory.EmptyDividerItemDecoration
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter

class FragmentBookmark: BaseViewPagerFragment(R.layout.fragment_bookmark) {

    private lateinit var bookmarkRecyclerView: RecyclerView
    private lateinit var buttonSettings: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkRecyclerView = view.findViewById(R.id.recycler_view_news_bookmark)
        bookmarkRecyclerView.adapter = NewsAdapter(emptyList())
        bookmarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookmarkRecyclerView.addItemDecoration(EmptyDividerItemDecoration())

        buttonSettings = view.findViewById(R.id.button_settings)
        buttonSettings.setOnClickListener {  }

    }

}