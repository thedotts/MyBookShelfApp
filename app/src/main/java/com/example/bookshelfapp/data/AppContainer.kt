package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.BookShelfAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BookShelfAPIService by lazy{
        retrofit.create(BookShelfAPIService::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy{
        NetWorkBookShelfRepository(retrofitService)
    }
}

