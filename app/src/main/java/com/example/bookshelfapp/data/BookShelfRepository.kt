package com.example.bookshelfapp.data

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
    override suspend fun getFictionBooks(): BookItems = bookShelfAPIService.getFictionBooks()
    override suspend fun getMysteryBooks(): BookItems = bookShelfAPIService.getMysteryBooks()
    override suspend fun getCrimeBooks(): BookItems = bookShelfAPIService.getCrimeBooks()
    override suspend fun getFantasyBooks(): BookItems = bookShelfAPIService.getFantasyBooks()
}