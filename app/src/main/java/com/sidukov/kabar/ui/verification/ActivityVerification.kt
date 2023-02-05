package com.sidukov.kabar.ui.verification

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sidukov.kabar.R

class ActivityVerification: AppCompatActivity() {

    private lateinit var viewPager2Verification: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        viewPager2Verification = findViewById(R.id.viewpager_2_verification)
        viewPager2Verification.adapter = ViewPagerAdapterVerification(this)
        viewPager2Verification.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPager2Verification.isUserInputEnabled = false

    }

    fun openNextPage(position: Int){
        viewPager2Verification.setCurrentItem(position, true)
    }

}