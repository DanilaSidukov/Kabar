package com.sidukov.kabar.ui.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.color.MaterialColors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Settings.Companion.AUTH_GOOGLE
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.data.settings.Settings.Companion.SERVICE_ID
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.createprofile.ActivityCreateProfile
import com.sidukov.kabar.ui.news.ActivityGeneral

class ActivitySignUp() : AppCompatActivity() {

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

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        println("USER IN ACTIVITY START = $currentUser")
        if (currentUser != null) {
            currentUser.reload()
            startActivity(
                Intent(this, ActivityGeneral::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
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

                            email.backgroundTintList =
                                ColorStateList.valueOf(MaterialColors.getColor(email,
                                    R.attr.color_error_red))
                            password.backgroundTintList =
                                ColorStateList.valueOf(MaterialColors.getColor(email,
                                    R.attr.color_error_red))
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            } else {
                email.backgroundTintList =
                    ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                password.backgroundTintList =
                    ColorStateList.valueOf(MaterialColors.getColor(email, R.attr.color_error_red))
                Toast.makeText(this,
                    "Please, input appropriate email and password",
                    Toast.LENGTH_SHORT).show()
            }
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SERVICE_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        buttonGoogle = findViewById(R.id.button_google)
        buttonGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, request_code)
        }

        textLogin = findViewById(R.id.already_have_an_account)
        val wordToSpan: Spannable = SpannableString(textLogin.text)
        val referenceStart = wordToSpan.indexOf("Login")
        val referenceEnd = referenceStart + 5
        wordToSpan.setSpan(
            ForegroundColorSpan(Color.rgb(24, 119, 242)),
            referenceStart, referenceEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ResourcesCompat.getFont(this, R.font.poppins_bold)?.let { fontTypeface ->
            wordToSpan.setSpan(
                TypefaceSpan(fontTypeface),
                referenceStart, referenceEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(
                    Intent(this@ActivitySignUp, ActivityLogin::class.java)
                )
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

        }
        wordToSpan.setSpan(clickableSpan,
            referenceStart,
            referenceEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textLogin.text = wordToSpan
        textLogin.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, ActivityGeneral::class.java).also {
                    it.putExtra("auth", AUTH_GOOGLE)
                }
                )
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