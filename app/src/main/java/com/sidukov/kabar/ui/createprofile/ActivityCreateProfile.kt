package com.sidukov.kabar.ui.createprofile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.CacheForImage
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.data.settings.Settings.Companion.FILE_NAME
import com.sidukov.kabar.data.settings.Settings.Companion.PROFILE_FULLNAME
import com.sidukov.kabar.data.settings.Settings.Companion.PROFILE_PHONENUMBER
import com.sidukov.kabar.ui.checkEmailLengthAndNull
import com.sidukov.kabar.ui.checkOnLengthAndNull
import com.sidukov.kabar.ui.news.ActivityGeneral

class ActivityCreateProfile : AppCompatActivity() {

    private lateinit var buttonNext: Button
    private lateinit var userName: TextView
    private lateinit var fullName: TextView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView

    private lateinit var imageButtonAddAvatar: ImageButton
    private lateinit var imageAvatar: ImageView
    private var pickedPhoto: Uri? = null
    private var pickedBitmap: Bitmap? = null

    private val auth = Firebase.auth
    val cache = CacheForImage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        imageButtonAddAvatar = findViewById(R.id.button_add_avatar)
        imageAvatar = findViewById(R.id.avatar)

        imageButtonAddAvatar.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1)
            } else {
                val galeriIntext =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext, 2)
            }
        }

        userName = findViewById(R.id.edit_text_username)
        fullName = findViewById(R.id.edit_text_full_name)
        email = findViewById(R.id.edit_text_email_address)
        phoneNumber = findViewById(R.id.edit_text_phone_number)

        email.text = this.intent.getStringExtra(EMAIL_KEY)

        val settings = Settings(this)

        buttonNext = findViewById(R.id.button_next)
        buttonNext.setOnClickListener {
            if (checkOnLengthAndNull(userName.text.toString())
                || checkOnLengthAndNull(fullName.text.toString())
                && checkEmailLengthAndNull(email.text.toString())
                && checkNumberLengthAndNull(phoneNumber.text.toString())
            ) {

                settings.saveProfileUsername = userName.text.toString()
                settings.saveProfileEmail = email.text.toString()
                settings.saveProfilePhoneNumber = phoneNumber.text.toString()
                settings.saveProfileFullname = fullName.text.toString()

                startActivity(
                    Intent(this, ActivityGeneral::class.java)
                )
            } else {
                Toast.makeText(this, "Please fill necessary fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkNumberLengthAndNull(string: String): Boolean {
        return string.length >= 11 && string.isNotEmpty()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeriIntext =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            val cache = CacheForImage(this)
            val bitmapFromCache = MediaStore.Images.Media.getBitmap(this.contentResolver, cache.getUriByFileName("profile_avatar"))
            if (pickedPhoto != null && bitmapFromCache == null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                    pickedBitmap = ImageDecoder.decodeBitmap(source)
                    cache.saveImageToCache(pickedBitmap!!, FILE_NAME)
                    imageAvatar.setImageBitmap(pickedBitmap)
                } else {
                    pickedBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
                    cache.saveImageToCache(pickedBitmap!!, FILE_NAME)
                    imageAvatar.setImageBitmap(pickedBitmap)
                }
            } else imageAvatar.setImageBitmap(bitmapFromCache)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}