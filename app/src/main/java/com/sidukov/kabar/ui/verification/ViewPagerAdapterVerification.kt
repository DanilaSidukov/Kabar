package com.sidukov.kabar.ui.verification

import android.util.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.FragmentReplacer

class ViewPagerAdapterVerification(
    container: FragmentActivity,
) : FragmentStateAdapter(container), FragmentReplacer {

    companion object {
        private var PAGE_COUNTER = 3
    }

    private val mapOfFragment = ArrayMap<Int, BaseViewPagerFragment>()

    override fun getItemCount() = PAGE_COUNTER

    override fun createFragment(position: Int): Fragment {
        return mapOfFragment[position] ?: replaceDef(position, false)
    }

    override fun containsItem(itemId: Long): Boolean {
        var isContains = false
        mapOfFragment.values.forEach {
            if (it.pageId == itemId) {
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
        val fragment = when (position) {
            0 -> VerificationFragmentOtp()
            1 -> VerificationFragmentReset()
            2 -> VerificationFragmentCongratulation()
            else -> throw  IllegalStateException()
        }
        replace(position, fragment, isNotify)
        return fragment
    }

    override fun getItemId(position: Int): Long =
        mapOfFragment[position]?.pageId ?: super.getItemId(position)

}