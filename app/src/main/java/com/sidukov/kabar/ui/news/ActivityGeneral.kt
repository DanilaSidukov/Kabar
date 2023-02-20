package com.sidukov.kabar.ui.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.CacheForImage
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.ui.NewsApplication
import javax.inject.Inject

class ActivityGeneral : AppCompatActivity(), OpenAllNewsFragment {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var accountViewModel: AccountViewModel

    private lateinit var bottomNavigationBar: BottomNavigationView
    private lateinit var viewPagerGeneral: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_layout)
        NewsApplication.appComponent.inject(this)
        accountViewModel = injectViewModel(viewModelFactory)

        viewPagerGeneral = findViewById(R.id.view_pager2_general)
        viewPagerGeneral.adapter = ViewPagerGeneralAdapter(this)
        viewPagerGeneral.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPagerGeneral.isUserInputEnabled = false

        val cache = CacheForImage(this)

        bottomNavigationBar = findViewById(R.id.bottom_navbar)
        bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    viewPagerGeneral.setCurrentItem(0, true)
                }
                R.id.menu_explore -> {
                    viewPagerGeneral.setCurrentItem(1, true)
                }
                R.id.menu_bookmark -> {
                    viewPagerGeneral.setCurrentItem(2, true)
                }
                R.id.menu_profile -> {
                    viewPagerGeneral.setCurrentItem(3, true)
                }
                else -> {}
            }
            true
        }

    }

    override fun openAllNewsFragment() {
        viewPagerGeneral.setCurrentItem(0, true)
    }

}

interface OpenAllNewsFragment {
    fun openAllNewsFragment()
}