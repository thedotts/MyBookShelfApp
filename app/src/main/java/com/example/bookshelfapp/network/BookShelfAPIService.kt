package com.example.bookshelfapp.network

import com.example.bookshelfapp.model.Items
import retrofit2.http.GET

interface BookShelfAPIService {
    @GET("volumes?q=incategories+Mistery")
    suspend fun getFictionBooks():Items

    @GET("volumes?q=incategories+Mystery")
    suspend fun getMysteryBooks():Items

    @GET("volumes?q=incategories+Crime")
    suspend fun getCrimeBooks():Items

    @GET("volumes?q=incategories+Fantasy")
    suspend fun getFantasyBooks():Items
}