package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.Items
import com.example.bookshelfapp.network.BookShelfAPIService

interface BookShelfRepository {
    suspend fun getFictionBooks(): Items
    suspend fun getMysteryBooks(): Items
    suspend fun getCrimeBooks(): Items
    suspend fun getFantasyBooks(): Items
}

class NetWorkBookShelfRepository(
    private val bookShelfAPIService: BookShelfAPIService
):BookShelfRepository{
    override suspend fun getFictionBooks(): Items = bookShelfAPIService.getFictionBooks()
    override suspend fun getMysteryBooks(): Items = bookShelfAPIService.getMysteryBooks()
    override suspend fun getCrimeBooks(): Items = bookShelfAPIService.getCrimeBooks()
    override suspend fun getFantasyBooks(): Items = bookShelfAPIService.getFantasyBooks()
}