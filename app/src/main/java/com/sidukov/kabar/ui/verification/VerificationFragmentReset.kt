package com.sidukov.kabar.ui.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment

class VerificationFragmentReset: BaseViewPagerFragment(R.layout.verification_fragment_reset) {

    private lateinit var buttonSubmitReset: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSubmitReset = view.findViewById(R.id.button_submit_reset)
        buttonSubmitReset.setOnClickListener {
            (activity as? ActivityVerification)?.openNextPage(2)
        }

    }

}