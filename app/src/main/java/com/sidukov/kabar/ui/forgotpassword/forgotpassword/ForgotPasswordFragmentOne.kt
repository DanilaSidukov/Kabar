package com.sidukov.kabar.ui.forgotpassword.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment

class ForgotPasswordFragmentOne() : BaseViewPagerFragment(R.layout.forgot_password_fragment_one),
    OnRadioButtonClicked {

    private lateinit var radioGroupPassword: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forgot_password_fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroupPassword = view.findViewById(R.id.radio_group_password)
        radioGroupPassword.setOnCheckedChangeListener {
            _, checkedId ->
            when (checkedId){
                R.id.radio_button1 -> onRadioButtonClicked()
                R.id.radio_button2 -> onRadioButtonClicked()
            }
        }
    }

    override fun onRadioButtonClicked() {
        (activity as? ForgotPasswordActivity)?.onRadioButtonClicked()
    }

}