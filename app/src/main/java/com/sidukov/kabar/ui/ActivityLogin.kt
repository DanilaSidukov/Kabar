package com.sidukov.kabar.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.color.MaterialColors
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.ui.news.AccountViewModel
import com.sidukov.kabar.ui.news.ActivityGeneral
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class ActivityLogin : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var accountViewModel: AccountViewModel

    private lateinit var email: EditText
    private lateinit var password: EditText

    private lateinit var buttonLogin: Button

    private lateinit var textSignUp: TextView
    private lateinit var imageShow: ImageView
    private lateinit var imageHide: ImageView

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        NewsApplication.appComponent.inject(this)
        accountViewModel = injectViewModel(viewModelFactory)

        email = findViewById(R.id.edit_input_login)
        password = findViewById(R.id.edit_input_password)

        imageShow = findViewById(R.id.image_show)
        imageHide = findViewById(R.id.image_hide)
        imageHide.visibility = View.VISIBLE
        imageShow.visibility = View.GONE
        password.transformationMethod = PasswordTransformationMethod.getInstance()
        imageHide.setOnClickListener {
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageShow.visibility = View.VISIBLE
            imageHide.visibility = View.GONE
        }
        imageShow.setOnClickListener {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            imageShow.visibility = View.GONE
            imageHide.visibility = View.VISIBLE
        }

        textSignUp = findViewById(R.id.text_sign_up)
        textSignUp.setOnClickListener {
            startActivity(
                Intent(
                    this, ActivitySignUp::class.java
                )
            )
        }

        buttonLogin = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            auth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(
                        Intent(
                            this, ActivityGeneral::class.java
                        ).also {
                            it.putExtra(EMAIL_KEY, emailText)
                        }
                    )
                } else {
                    email.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                    password.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}