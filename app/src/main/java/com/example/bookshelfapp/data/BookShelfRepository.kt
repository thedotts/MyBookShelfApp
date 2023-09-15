package com.example.bookshelfapp.data

import android.util.Log
import com.example.bookshelfapp.model.BookItems
import com.example.bookshelfapp.network.BookShelfAPIService

interface BookShelfRepository {
    suspend fun getFictionBooks(): BookItems
    suspend fun getMysteryBooks(): BookItems
    suspend fun getCrimeBooks(): BookItems
    suspend fun getFantasyBooks(): BookItems
}

class NetWorkBookShelfRepository(
    private val bookShelfAPIService: BookShelfAPIService
):BookShelfRepository{
    override suspend fun getFictionBooks(): BookItems   {
        Log.d("BookShelfRepository","getFictionBooks")
        return bookShelfAPIService.getFictionBooks()}
    override suspend fun getMysteryBooks(): BookItems {
        Log.d("BookShelfRepository","getMysteryBooks")
        return bookShelfAPIService.getMysteryBooks()
    }
    override suspend fun getCrimeBooks(): BookItems = bookShelfAPIService.getCrimeBooks()
    override suspend fun getFantasyBooks(): BookItems = bookShelfAPIService.getFantasyBooks()
}