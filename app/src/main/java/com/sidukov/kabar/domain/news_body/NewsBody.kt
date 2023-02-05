package com.sidukov.kabar.domain.news_body

data class NewsBody(
    val nextPage: String,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)