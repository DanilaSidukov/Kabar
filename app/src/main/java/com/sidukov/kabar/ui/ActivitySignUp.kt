package com.sidukov.kabar.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.sidukov.kabar.R
import com.sidukov.kabar.ui.createprofile.ActivityCreateProfile
import com.sidukov.kabar.ui.news.ActivityGeneral

class ActivitySignUp: AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: Button

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email = findViewById(R.id.edit_input_login)
        password = findViewById(R.id.edit_input_password)

        buttonLogin = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            if (checkEmailLengthAndNull(email.text.toString()) && checkOnLengthAndNull(password.text.toString())){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful){
                            startActivity(
                                Intent(this, ActivityCreateProfile::class.java).also {
                                    it.putExtra("email", email.text.toString())
                                }
                            )
                        }
                    }
            }
        }
    }
}

fun checkEmailLengthAndNull(string: String): Boolean {
    return string.isNotEmpty() && string.length > 9
}

fun checkOnLengthAndNull(string: String): Boolean{
    return string.isNotEmpty() && string.length > 6
}