package com.sidukov.kabar.ui.forgotpassword.forgotpassword

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.ViewPagerAdapterPassword

class ForgotPasswordActivity : AppCompatActivity(), OnRadioButtonClicked {

    private lateinit var forgotPasswordToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password)

        forgotPasswordToolbar = findViewById(R.id.forgot_password_toolbar)
        setSupportActionBar(forgotPasswordToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager2 = findViewById(R.id.view_pager2_forgott_password)
        viewPager2.adapter = ViewPagerAdapterPassword(this)
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPager2.isUserInputEnabled = false

    }
    override fun onRadioButtonClicked() {
        viewPager2.isUserInputEnabled = true
        viewPager2.setCurrentItem(1, true)
    }

}

interface OnRadioButtonClicked {
    fun onRadioButtonClicked()
}