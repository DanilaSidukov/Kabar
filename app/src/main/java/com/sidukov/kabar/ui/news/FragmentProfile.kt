package com.sidukov.kabar.ui.news

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sidukov.kabar.R
import com.sidukov.kabar.data.settings.Profile
import com.sidukov.kabar.data.settings.Settings.Companion.AUTH_GOOGLE
import com.sidukov.kabar.data.settings.Settings.Companion.FILE_NAME
import com.sidukov.kabar.ui.ActivityLogin
import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class FragmentProfile: BaseViewPagerFragment(R.layout.fragment_profile) {

    private lateinit var name: TextView
    private lateinit var nickName: TextView
    private lateinit var email: TextView
    private lateinit var buttonExit: Button
    private lateinit var imageProfile: ImageView
    private lateinit var imageButton: ImageButton

    private lateinit var googleSignInClient: GoogleSignInClient
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val cloudFirebase = Firebase.firestore
    private lateinit var storageReference: StorageReference

    private var pickedPhoto: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN)

        name = view.findViewById(R.id.text_user_name_profile)
        nickName = view.findViewById(R.id.text_user_nickname_profile)
        imageProfile = view.findViewById(R.id.avatar_profile)
        imageButton = view.findViewById(R.id.button_add_avatar_profile)
        email = view.findViewById(R.id.text_email_profile)


        val authIntent = requireActivity().intent.getStringExtra("auth").toString()
        if (authIntent == AUTH_GOOGLE){
            name.text = currentUser?.displayName.toString()
            nickName.text = currentUser?.displayName.toString()
            email.text = currentUser?.email.toString()
            Picasso.get().load(currentUser?.photoUrl).into(imageProfile)
            imageButton.visibility = View.GONE
        } else {
            imageButton.visibility = View.VISIBLE

            val emailOfUser = currentUser?.email.toString()

            cloudFirebase.collection("users")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty){
                        Toast.makeText(requireContext(), "Error: empty database", Toast.LENGTH_SHORT).show()
                    } else {
                        val listProfile: List<Profile> = querySnapshot.toObjects(Profile::class.java)
                        listProfile.forEach { profile ->
                            if (profile.email.toString() == emailOfUser) {
                                name.text = profile.nickName ?: "No name"
                                nickName.text = profile.nickName ?: "No Nickname"
                                email.text = profile.email ?: "No email"

                                storageReference = FirebaseStorage.getInstance().reference
                                storageReference.child("avatars/$emailOfUser").downloadUrl
                                    .addOnSuccessListener {
                                        Picasso.get().load(it).into(imageProfile)
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(requireContext(), "Download error: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                                    }

                                return@addOnSuccessListener
                            }
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                }

        }

        imageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext, 2)
            }
        }

        buttonExit = view.findViewById(R.id.button_exit)
        buttonExit.setOnClickListener {

            startActivity(
                Intent(
                    this.requireActivity(), ActivityLogin::class.java
                )
            )
            if (authIntent == AUTH_GOOGLE) googleSignInClient.signOut()
            else Firebase.auth.signOut()
        }

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

    private fun uploadImage(){

        val fileName = currentUser?.email.toString()

        storageReference = FirebaseStorage.getInstance().getReference("avatars/$fileName")

        storageReference.putFile(pickedPhoto!!)
            .addOnSuccessListener { Toast.makeText(requireContext(),"New avatar added",Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(requireContext(), "Error: ${it.localizedMessage}", Toast.LENGTH_SHORT).show() }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null) {
                setImage(pickedPhoto!!)
                uploadImage()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setImage(pickedPhoto: Uri){
        Picasso.get().load(pickedPhoto).into(imageProfile)
        currentUser?.updateProfile(UserProfileChangeRequest.Builder()
            .setPhotoUri(pickedPhoto)
            .build()
        )
    }

}