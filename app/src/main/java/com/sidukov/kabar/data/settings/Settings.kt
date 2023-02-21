package com.sidukov.kabar.data.settings

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import com.squareup.picasso.Cache
import java.io.Serializable
import javax.inject.Inject

open class Settings @Inject constructor(context: Context) {

    companion object {
        const val SERVICE_ID =
            //"410372047702-lma0ap8putv5vbrl9400sj8jc7oqdcsr.apps.googleusercontent.com"
        "843635545086-5a5tfcldjk4mplr9e7gorvf65gm9et14.apps.googleusercontent.com"

        const val DATABASE_USERS_KEY = "users"
        const val KABAR_PROFILE_KEY = "kabar"

        const val API_KEY = "pub_16526c5b9ee62502bac4aaee39680d3370436"
        const val EMAIL_KEY = "email"
        const val FILE_NAME = "profile_avatar"

        val TAG: String = Cache::class.java.simpleName
        const val CHILD_DIR = "images"
        const val TEMP_FILE_NAME = "img"
        const val FILE_EXTENSION = ".png"
        const val COMPRESS_QUALITY = 100

    }

}

data class Profile(
    val nickName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val imageUri: Uri? = null
)