package com.sidukov.kabar.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.color.MaterialColors
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.ui.createprofile.ActivityCreateProfile
import com.sidukov.kabar.ui.news.ActivityGeneral

class ActivitySignUp : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: Button

    private lateinit var textLogin: TextView
    private lateinit var imageShow: ImageView
    private lateinit var imageHide: ImageView

    private val auth = Firebase.auth

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        println("user = $currentUser")
        if (currentUser != null) {
            println("i'm here")
            currentUser.reload()
            startActivity(
                Intent(this, ActivityGeneral::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email = findViewById(R.id.edit_input_login)
        password = findViewById(R.id.edit_input_password)

        imageShow = findViewById(R.id.image_show)
        imageHide = findViewById(R.id.image_hide)
        imageShow.visibility = View.GONE
        password.transformationMethod = PasswordTransformationMethod.getInstance()
        imageHide.setOnClickListener {
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageShow.visibility = View.VISIBLE
            imageHide.visibility = View.GONE
        }
        imageShow.setOnClickListener{
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            imageShow.visibility = View.GONE
            imageHide.visibility = View.VISIBLE
        }

        textLogin = findViewById(R.id.text_login)

        buttonLogin = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            if (checkEmailLengthAndNull(email.text.toString()) && checkOnLengthAndNull(password.text.toString())) {
                val emailText = email.text.toString()
                email.backgroundTintList =
                    ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_white))
                password.backgroundTintList =
                    ColorStateList.valueOf(MaterialColors.getColor(password, R.attr.color_white))
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailText, password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(
                                Intent(this, ActivityCreateProfile::class.java).also {
                                    it.putExtra(EMAIL_KEY, emailText)
                                }
                            )
                        } else {
                            Toast.makeText(this,
                                "Something went wrong, please, try again",
                                Toast.LENGTH_SHORT).show()
                            email.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                            password.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                        }
                    }
            } else {
                email.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                password.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                Toast.makeText(this, "Please, input appropriate email and password", Toast.LENGTH_SHORT).show()
            }
        }

        textLogin.setOnClickListener {
            startActivity(
                Intent(this, ActivityLogin::class.java)
            )
        }
    }
}

fun checkEmailLengthAndNull(string: String): Boolean {
    return string.isNotEmpty() && string.length > 9
}

fun checkOnLengthAndNull(string: String): Boolean {
    return string.isNotEmpty() && string.length > 6
}