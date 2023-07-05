package com.sidukov.kabar.data.settings

import android.content.Context
import javax.inject.Inject

open class Settings @Inject constructor(context: Context) {

    companion object {
        const val SERVICE_ID =
        "843635545086-5a5tfcldjk4mplr9e7gorvf65gm9et14.apps.googleusercontent.com"

        const val AUTH_GOOGLE = "google"

        const val API_KEY = "pub_16526c5b9ee62502bac4aaee39680d3370436"
        const val EMAIL_KEY = "email"

    }


}