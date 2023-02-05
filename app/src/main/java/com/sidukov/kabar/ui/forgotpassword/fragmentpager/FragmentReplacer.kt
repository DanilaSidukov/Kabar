package com.sidukov.kabar.ui.forgotpassword.fragmentpager

interface FragmentReplacer {
    fun replace(position: Int, newFragment: BaseViewPagerFragment, isNotify: Boolean = true)
    fun replaceDef(position: Int, isNotify: Boolean = true): BaseViewPagerFragment
}