package com.sidukov.kabar.data.settings

import android.hardware.camera2.CameraManager.AvailabilityCallback
import javax.inject.Inject

class AccountRepository @Inject constructor(
    val settings: Settings
) {

    fun saveProfile(userName: String?, fullName: String?, email: String?, phoneNumber:String?){
        settings.saveProfileUsername = userName
        settings.saveProfileFullname = fullName
        settings.saveProfileEmail = email
        settings.saveProfilePhoneNumber = phoneNumber
    }

    suspend fun getUsername() = settings.saveProfileUsername
    suspend fun getFullname() = settings.saveProfileFullname
    suspend fun getEmail() = settings.saveProfileEmail
    suspend fun getPhoneNumber() = settings.saveProfilePhoneNumber

    fun saveGoogleProfile(email: String?, userName: String?, avatar: String?){
        settings.saveGoogleEmail = email
        settings.saveGoogleUsername = userName
        settings.saveGoogleImageUri = avatar
    }

    suspend fun getEmailGoogle() = settings.saveGoogleEmail
    suspend fun getUserNameGoogle() = settings.saveGoogleUsername
    suspend fun getImageGoogle() = settings.saveGoogleImageUri

}