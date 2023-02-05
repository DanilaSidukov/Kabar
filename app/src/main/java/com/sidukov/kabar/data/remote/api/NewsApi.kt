package com.sidukov.kabar.data.remote.api

import com.sidukov.kabar.domain.NewsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {

    @GET("/{sources}")
    suspend fun getSourceNews(
    @Path ("sources") sources : String = "sources.json"
    ): NewsItem

}