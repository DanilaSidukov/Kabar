package com.sidukov.kabar.ui.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment

class VerificationFragmentOtp: BaseViewPagerFragment(R.layout.verification_fragment_otp) {

    private lateinit var buttonVerify: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.verification_fragment_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonVerify = view.findViewById(R.id.button_verify)
        buttonVerify.setOnClickListener {
            (activity as? ActivityVerification)?.openNextPage(1)

        }
    }

}