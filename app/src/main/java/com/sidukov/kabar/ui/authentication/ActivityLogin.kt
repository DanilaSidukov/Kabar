package com.sidukov.kabar.ui.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.color.MaterialColors
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.data.settings.Settings.Companion.AUTH_GOOGLE
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.news.ActivityGeneral
import kotlinx.android.synthetic.main.forgot_password_fragment_one.*

class ActivityLogin : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText

    private lateinit var textSignUp: TextView

    private lateinit var buttonLogin: Button
    private lateinit var buttonGoogle: Button
    val request_code = 2

    private lateinit var imageShow: ImageView
    private lateinit var imageHide: ImageView

    private val auth = Firebase.auth
    val database = Firebase.database.reference
    lateinit var googleSignInClient: GoogleSignInClient

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        NewsApplication.appComponent.inject(this)

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


        textSignUp = findViewById(R.id.text_don_t_have_an_account)
        val wordToSpan: Spannable = SpannableString(textSignUp.text)
        val referenceStart = wordToSpan.indexOf("Sign Up")
        val referenceEnd = referenceStart + 7
        wordToSpan.setSpan(
            ForegroundColorSpan(android.graphics.Color.rgb(24, 119,242)),
            referenceStart, referenceEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ResourcesCompat.getFont(this, R.font.poppins_bold)?.let { fontTypeface ->
            wordToSpan.setSpan(
                TypefaceSpan(fontTypeface),
                referenceStart, referenceEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        val clickableSpan = object: ClickableSpan(){
            override fun onClick(widget: View) {
                startActivity(
                    Intent(this@ActivityLogin, ActivitySignUp::class.java)
                )
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        wordToSpan.setSpan(clickableSpan, referenceStart, referenceEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE )
        textSignUp.text = wordToSpan
        textSignUp.movementMethod = LinkMovementMethod.getInstance()


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

        buttonGoogle = findViewById(R.id.button_google)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Settings.SERVICE_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        buttonGoogle.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, request_code)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>){
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) updateUi(account)
        } catch (e: ApiException) {
            Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful){
                startActivity(
                    Intent(this, ActivityGeneral::class.java).also {
                        it.putExtra("auth", AUTH_GOOGLE)
                    }
                )
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Google login error: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

}