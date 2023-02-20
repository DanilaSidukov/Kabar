package com.sidukov.kabar.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidukov.kabar.data.settings.AccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

open class AccountViewModel @Inject constructor(
    val repository: AccountRepository
): ViewModel() {



    fun setProfileAccount(userName: String?, fullName: String?, email: String?, phoneNumber:String?){
        viewModelScope.launch {
            repository.saveProfile(userName, fullName, email, phoneNumber)
        }
    }

    suspend fun getProfileUsername(): String? {
        return repository.getUsername()
    }

    suspend fun getProfileFullName(): String? {
        return repository.getFullname()
    }

    suspend fun getProfileEmail(): String? {
        return repository.getEmail()
    }

    suspend fun getProfilePhoneNumber(): String? {
        return repository.getPhoneNumber()
    }

    fun setGoogleAccount(email: String?, userName: String?, avatar: String?){
        viewModelScope.launch {
            repository.saveGoogleProfile(email, userName, avatar)
        }
    }

    suspend fun getGoogleEmail(): String?{
        return repository.getEmailGoogle()
    }

    suspend fun getGoogleUserName(): String?{
        return repository.getUserNameGoogle()
    }

    suspend fun getGoogleImage(): String?{
        return repository.getImageGoogle()
    }

}