package com.sidukov.kabar.ui.forgotpassword.fragmentpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.news.newscategory.NewsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.random.Random

abstract class BaseViewPagerFragment(private val layoutId: Int) : Fragment() {

    val pageId = Random.nextLong(2021, 2021 * 3)
    var pagePosition = -1
    protected lateinit var fragmentReplacer: FragmentReplacer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setPageInfo(pagePosition: Int, fragmentReplacer: FragmentReplacer) {
        this.pagePosition = pagePosition
        this.fragmentReplacer = fragmentReplacer
    }

}