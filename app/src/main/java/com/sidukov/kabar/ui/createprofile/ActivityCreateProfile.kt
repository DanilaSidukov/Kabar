package com.sidukov.kabar.ui.createprofile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sidukov.kabar.R
import com.sidukov.kabar.data.local.Profile
import com.sidukov.kabar.data.settings.Settings.Companion.EMAIL_KEY
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.authentication.checkEmailLengthAndNull
import com.sidukov.kabar.ui.authentication.checkOnLengthAndNull
import com.sidukov.kabar.ui.news.ActivityGeneral
import com.squareup.picasso.Picasso

class ActivityCreateProfile : AppCompatActivity() {

    private lateinit var buttonNext: Button
    private lateinit var userName: TextView
    private lateinit var fullName: TextView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView

    private lateinit var imageButtonAddAvatar: ImageButton
    private lateinit var imageAvatar: ImageView
    private var pickedPhoto: Uri? = null

    private val cloudFirebase = Firebase.firestore
    private val currentUser = Firebase.auth.currentUser
    val database = FirebaseDatabase.getInstance().getReference("/users_data")
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        NewsApplication.appComponent.inject(this)

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

        email.text = this.intent.getStringExtra(EMAIL_KEY).toString()

        buttonNext = findViewById(R.id.button_next)
        buttonNext.setOnClickListener {
            if (checkOnLengthAndNull(userName.text.toString())
                || checkOnLengthAndNull(fullName.text.toString())
                && checkEmailLengthAndNull(email.text.toString())
                && checkNumberLengthAndNull(phoneNumber.text.toString())
            ) {

                val user = Profile(
                        userName.text.toString(),
                        email.text.toString(),
                        phoneNumber.text.toString(),
                    )

                println("picked photo before sending = $pickedPhoto")

                cloudFirebase.collection("users")
                    .document(currentUser?.email.toString())
                    .set(user)
                    .addOnSuccessListener { Toast.makeText(this, "Successful!", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener { Toast.makeText(this, "Error: ${it.localizedMessage}", Toast.LENGTH_SHORT).show() }

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

    private fun uploadImage(){

        val fileName = currentUser?.email.toString()

        storageReference = FirebaseStorage.getInstance().getReference("avatars/$fileName")

        storageReference.putFile(pickedPhoto!!)
            .addOnSuccessListener { Toast.makeText(this,"Avatar added",Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(this, "Error: ${it.localizedMessage}", Toast.LENGTH_SHORT).show() }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            println("picked photo create profile = $pickedPhoto")
            if (pickedPhoto != null) {
                Picasso.get().load(pickedPhoto).into(imageAvatar)
                uploadImage()
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}