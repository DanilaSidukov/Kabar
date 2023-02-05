package com.sidukov.kabar.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.newscategory.ViewPagerNewsAdapter

class FragmentHome : BaseViewPagerFragment(R.layout.fragment_news) {

    private lateinit var viewPagerNews: ViewPager2
    private lateinit var tabLayoutNews: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerNews = view.findViewById(R.id.view_pager2_news)
        viewPagerNews.adapter = ViewPagerNewsAdapter(this)
        viewPagerNews.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPagerNews.isUserInputEnabled = true

        tabLayoutNews = view.findViewById(R.id.table_layout_news)
        tabLayoutNews.tabGravity = TabLayout.GRAVITY_FILL

        TabLayoutMediator(tabLayoutNews, viewPagerNews) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.all)
                1 -> getString(R.string.business)
                2 -> getString(R.string.entertainment)
                3 -> getString(R.string.health_and_food)
                4 -> getString(R.string.science)
                5 -> getString(R.string.sports)
                6 -> getString(R.string.technology)
                else -> "Error"
            }
        }.attach()
    }
}