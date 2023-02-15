package com.sidukov.kabar.ui.createprofile

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toIcon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.CacheForImage
import com.sidukov.kabar.data.settings.Profile
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.data.settings.Settings.Companion.CHILD_DIR
import com.sidukov.kabar.data.settings.Settings.Companion.COMPRESS_QUALITY
import com.sidukov.kabar.data.settings.Settings.Companion.FILE_EXTENSION
import com.sidukov.kabar.data.settings.Settings.Companion.TAG
import com.sidukov.kabar.data.settings.Settings.Companion.TEMP_FILE_NAME
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.checkEmailLengthAndNull
import com.sidukov.kabar.ui.checkOnLengthAndNull
import com.squareup.picasso.Cache
import org.jetbrains.annotations.Nullable
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.CacheRequest

class ActivityCreateProfile: AppCompatActivity() {

    private lateinit var buttonNext: Button
    private lateinit var userName: TextView
    private lateinit var fullName: TextView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView

    private lateinit var imageButtonAddAvatar: ImageButton
    private lateinit var imageAvatar: ImageView
    var pickedPhoto: Uri? = null
    var pickedBitmap: Bitmap? = null

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

        email.text = Intent().getStringExtra("email")

        val cache = CacheForImage(this)
        val uri = cache.saveToCacheAndGetUri(pickedBitmap!!)

        val profile = Profile(
            username = userName.text.toString(),
            avatar = uri,
            email = email.text.toString(),
            phoneNumber = phoneNumber.text.toString(),
            fullName = fullName.text.toString()
        )

        buttonNext = findViewById(R.id.button_next)
        buttonNext.setOnClickListener {
            if (checkOnLengthAndNull(userName.text.toString())
                && checkOnLengthAndNull(fullName.text.toString())
                && checkEmailLengthAndNull(email.text.toString())
                && checkNumberLengthAndNull(phoneNumber.text.toString())
            ) {

            }
        }

    }



    private fun checkNumberLengthAndNull(string: String): Boolean {
        return string.length > 11 && string.isNotEmpty()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null){
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                    pickedBitmap = ImageDecoder.decodeBitmap(source)
                    imageAvatar.setImageBitmap(pickedBitmap)
                } else {
                    pickedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
                    imageAvatar.setImageBitmap(pickedBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}