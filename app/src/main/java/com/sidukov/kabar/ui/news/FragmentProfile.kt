package com.sidukov.kabar.ui.news

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.CacheForImage
import com.sidukov.kabar.data.settings.Settings.Companion.FILE_NAME
import com.sidukov.kabar.data.settings.Settings.Companion.PROFILE_EMAIL
import com.sidukov.kabar.data.settings.Settings.Companion.PROFILE_FULLNAME
import com.sidukov.kabar.data.settings.Settings.Companion.PROFILE_PHONENUMBER
import com.sidukov.kabar.data.settings.Settings.Companion.PROFILE_USERNAME
import com.sidukov.kabar.data.settings.Settings.Companion.SERVICE_ID
import com.sidukov.kabar.ui.ActivityLogin
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment

class FragmentProfile: BaseViewPagerFragment(R.layout.fragment_profile) {

    private lateinit var name: TextView
    private lateinit var nickName: TextView
    private lateinit var email: TextView
    private lateinit var buttonExit: Button

    lateinit var googleSignInClient: GoogleSignInClient
    val auth = FirebaseAuth.getInstance()

    private lateinit var imageProfile: ImageView
    private lateinit var imageButton: ImageButton

    private var pickedPhoto: Uri? = null
    private var pickedBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cache = CacheForImage(this.requireActivity())
        pickedPhoto = cache.getUriByFileName(FILE_NAME)
        pickedBitmap =
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver,
                pickedPhoto)

        name = view.findViewById(R.id.text_user_name_profile)
        nickName = view.findViewById(R.id.text_user_nickname_profile)
        imageProfile = view.findViewById(R.id.avatar_profile)
        imageButton = view.findViewById(R.id.button_add_avatar_profile)
        email = view.findViewById(R.id.text_email_profile)

        val settings = com.sidukov.kabar.data.settings.Settings(requireContext())

        name.text = settings.saveProfileFullname
        nickName.text = settings.saveProfileUsername
        email.text = settings.saveProfileEmail
        imageProfile.setImageBitmap(pickedBitmap)

        imageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext, 2)
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SERVICE_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)

        buttonExit = view.findViewById(R.id.button_exit)
        buttonExit.setOnClickListener {

            settings.saveProfileUsername = null
            settings.saveProfileEmail = null
            settings.saveProfilePhoneNumber = null
            settings.saveProfileFullname = null

            settings.saveGoogleUsername = null
            settings.saveGoogleEmail = null

            startActivity(
                Intent(
                    this.requireActivity(), ActivityLogin::class.java
                )
            )
            googleSignInClient.signOut()
            Firebase.auth.signOut()
        }

//
//        val uriFromCache = cache.getUriByFileName("profile_avatar")
//        if (uriFromCache != null) {
//            val bitmapFromCache = MediaStore.Images.Media.getBitmap(this.activity!!.contentResolver, uriFromCache)
//            if (bitmapFromCache != null) imageProfile.setImageBitmap(bitmapFromCache)
//        }
    }

    @Deprecated("Deprecated in Java")
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
        val cache = CacheForImage(this.requireActivity())
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source =
                        ImageDecoder.createSource(requireContext().contentResolver, pickedPhoto!!)
                    pickedBitmap = ImageDecoder.decodeBitmap(source)
                    cache.saveImageToCache(pickedBitmap!!, FILE_NAME)
                    imageProfile.setImageBitmap(pickedBitmap)
                } else {
                    pickedBitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver,
                            pickedPhoto)
                    cache.saveImageToCache(pickedBitmap!!, FILE_NAME)
                    imageProfile.setImageBitmap(pickedBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}