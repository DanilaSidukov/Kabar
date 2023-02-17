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
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.color.MaterialColors
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.data.settings.Settings.Companion.GOOGLE_EMAIL
import com.sidukov.kabar.data.settings.Settings.Companion.GOOGLE_USERNAME
import com.sidukov.kabar.data.settings.Settings.Companion.SERVICE_ID
import com.sidukov.kabar.ui.createprofile.ActivityCreateProfile
import com.sidukov.kabar.ui.news.ActivityGeneral
import okhttp3.internal.platform.Platform

class ActivitySignUp : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonGoogle: Button

    private lateinit var textLogin: TextView
    private lateinit var imageShow: ImageView
    private lateinit var imageHide: ImageView

    private val auth = Firebase.auth

    val firebaseAuth = FirebaseAuth.getInstance()
    val request_code = 2
    lateinit var googleSignInClient: GoogleSignInClient

    private val currentUser = auth.currentUser

    val settings = Settings(this)

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        println("user = $currentUser")
        if (currentUser != null) {
            currentUser.reload()
            startActivity(
                Intent(this, ActivityGeneral::class.java)
            )
        }
        val accountGoogle = GoogleSignIn.getLastSignedInAccount(this)
        if (accountGoogle != null){
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
        imageHide.visibility = View.VISIBLE
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

                            email.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                            password.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            } else {
                email.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                password.backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                Toast.makeText(this, "Please, input appropriate email and password", Toast.LENGTH_SHORT).show()
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SERVICE_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        buttonGoogle.setOnClickListener {

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, request_code)

//            BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        .setServerClientId(SERVICE_ID)
//                        .setFilterByAuthorizedAccounts(true)
//                        .build()
//                ).build()
        }

        textLogin.setOnClickListener {
            startActivity(
                Intent(this, ActivityLogin::class.java)
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null){
                UpdateUI(account)
            }
        }catch (e: ApiException){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                settings.saveGoogleEmail = account.email.toString()
                settings.saveGoogleUsername = account.displayName.toString()
                startActivity(Intent(this, ActivityGeneral::class.java))
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

}


fun checkEmailLengthAndNull(string: String): Boolean {
    return string.isNotEmpty() && string.length > 9
}

fun checkOnLengthAndNull(string: String): Boolean {
    return string.isNotEmpty() && string.length > 6
}