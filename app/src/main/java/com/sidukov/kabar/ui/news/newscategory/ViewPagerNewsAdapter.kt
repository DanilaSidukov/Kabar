package com.sidukov.kabar.ui.news.newscategory

import android.util.ArrayMap
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.FragmentReplacer
import com.sidukov.kabar.ui.news.FragmentHome

class ViewPagerNewsAdapter(
    container: FragmentHome,
): FragmentStateAdapter(container), FragmentReplacer {

    companion object {
        private const val PAGE_COUNTER = 8
    }

    private val mapOfFragment = ArrayMap<Int, BaseViewPagerFragment>()

    override fun getItemCount() = PAGE_COUNTER

    override fun createFragment(position: Int): Fragment {
        return mapOfFragment[position]?: replaceDef(position, false)
    }

    override fun containsItem(itemId: Long): Boolean {
        var isContains = false
        mapOfFragment.values.forEach {
            if (itemId == it.pageId){
                isContains = false
                return@forEach
            }
        }
        return isContains
    }

    override fun replace(position: Int, newFragment: BaseViewPagerFragment, isNotify: Boolean) {
        newFragment.setPageInfo(
            pagePosition = position,
            fragmentReplacer = this
        )
        mapOfFragment[position] = newFragment
        if (isNotify) notifyItemChanged(position)
    }

    override fun replaceDef(position: Int, isNotify: Boolean): BaseViewPagerFragment {
        val fragment = when(position){
//            0 -> FragmentAllNews()
//            1 -> FragmentBusinessNews()
//            2 -> FragmentEntertainmentNews()
//            3 -> FragmentHealthAndFoodNews()
//            4 -> FragmentScienceNews()
//            5 -> FragmentSportsNews()
//            6 -> FragmentTechnologyNews()
//            7 -> FragmentPoliticsNews()
            else -> throw  IllegalStateException()
        }
        replace(position, fragment, isNotify)
        return fragment
    }

    override fun getItemId(position: Int) = mapOfFragment[position]?.pageId ?: super.getItemId(position)

}