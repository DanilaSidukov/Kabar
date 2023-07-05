package com.sidukov.kabar.data.local

data class Profile(
    val nickName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    var imageUri: String? = null,
)