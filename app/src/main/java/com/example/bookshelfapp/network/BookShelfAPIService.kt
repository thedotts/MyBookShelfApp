package com.example.bookshelfapp.network

import com.example.bookshelfapp.model.BookItems
import retrofit2.http.GET

interface BookShelfAPIService {
    @GET("volumes?q=incategories+Fiction&maxResults=10")
    suspend fun getFictionBooks():BookItems

    @GET("volumes?q=incategories+Mystery")
    suspend fun getMysteryBooks():BookItems

    @GET("volumes?q=incategories+Crime&maxResults=10")
    suspend fun getCrimeBooks():BookItems

    @GET("volumes?q=incategories+Fantasy&maxResults=10")
    suspend fun getFantasyBooks():BookItems
}