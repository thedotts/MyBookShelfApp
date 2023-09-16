package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.ui.screens.CategoryType

data class BookUiState(
    val categoryType: CategoryType = CategoryType.None,
    val bookList: List<Book> = emptyList(),
    val currentBook: Book? = null,
)