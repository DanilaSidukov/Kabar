package com.sidukov.kabar.domain

import java.io.Serializable
import java.util.Date

data class NewsItem(
    val newsImage: String?,
    val textCategory: String?,
    val titleText: String?,
    val description: String?,
    val authorImage: Int?,
    val author: String?,
    val date: Date?,
) : Serializable