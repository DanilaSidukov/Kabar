package com.sidukov.kabar.data.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.picasso.Cache
import javax.inject.Inject

class Settings @Inject constructor(context: Context) {

    companion object {
        const val SERVICE_ID =
            "724243461333-eehueikm04mu3kraocadiqansp60kn8t.apps.googleusercontent.com"

        const val API_KEY = "pub_16526c5b9ee62502bac4aaee39680d3370436"
        const val EMAIL_KEY = "email"
        const val USERNAME_KEY = "username"
        const val PROFILE_KEY = "profile"
        const val FILE_NAME = "profile_avatar"

        const val PROFILE_USERNAME: String = "username"
        const val PROFILE_FULLNAME: String = "full_name"
        const val PROFILE_PHONENUMBER: String = "phone_number"
        const val PROFILE_EMAIL: String = "profile_email"

        const val GOOGLE_USERNAME: String = "google_username"
        const val GOOGLE_EMAIL: String = "google_email"

        val TAG: String = Cache::class.java.simpleName
        const val CHILD_DIR = "images"
        const val TEMP_FILE_NAME = "img"
        const val FILE_EXTENSION = ".png"
        const val COMPRESS_QUALITY = 100

    }

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PROFILE_KEY, Context.MODE_PRIVATE)

    var saveProfileUsername: String?
        get() = sharedPreferences.getString(PROFILE_USERNAME, null)
        set(value) = sharedPreferences.edit {
            putString(PROFILE_USERNAME, value)
        }

    var saveProfileFullname: String?
    get() = sharedPreferences.getString(PROFILE_FULLNAME, null)
    set(value) = sharedPreferences.edit {
        putString(PROFILE_FULLNAME, value)
    }

    var saveProfilePhoneNumber: String?
    get() = sharedPreferences.getString(PROFILE_PHONENUMBER, null)
    set(value) = sharedPreferences.edit {
        putString(PROFILE_PHONENUMBER, value)
    }

    var saveProfileEmail: String?
    get() = sharedPreferences.getString(PROFILE_EMAIL, null)
    set(value) = sharedPreferences.edit {
        putString(PROFILE_EMAIL, value)
    }

    var deleteValueProfile =sharedPreferences.edit().clear().apply()

    var saveGoogleUsername: String?
    get() = sharedPreferences.getString(GOOGLE_USERNAME, null)
    set(value) = sharedPreferences.edit {
        putString(GOOGLE_USERNAME, value)
    }

    var saveGoogleEmail: String?
    get() = sharedPreferences.getString(GOOGLE_EMAIL, null)
    set(value) = sharedPreferences.edit {
        putString(GOOGLE_EMAIL, value)
    }
}