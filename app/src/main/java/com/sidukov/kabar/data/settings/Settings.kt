package com.sidukov.kabar.data.settings

import android.content.Context
import android.net.Uri
import com.squareup.picasso.Cache
import javax.inject.Inject

class Settings @Inject constructor(context: Context) {

    companion object {
        const val API_KEY = "pub_16526c5b9ee62502bac4aaee39680d3370436"

        val TAG: String = Cache::class.java.simpleName
        const val CHILD_DIR = "images"
        const val TEMP_FILE_NAME = "img"
        const val FILE_EXTENSION = ".png"
        const val COMPRESS_QUALITY = 100

    }

}

data class Profile(
    val username: String,
    val avatar: Uri?,
    val email: String,
    val phoneNumber: String,
    val fullName: String,
)