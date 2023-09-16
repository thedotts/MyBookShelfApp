package com.example.bookshelfapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookShelfApplication
import com.example.bookshelfapp.data.BookShelfRepository
import com.example.bookshelfapp.data.BookUiState
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookShelfUiState{
    data class Success(val books: BookItems): BookShelfUiState
    object Error: BookShelfUiState
    object Loading: BookShelfUiState
}

enum class CategoryType{
    Fiction,
    Mystery,
    Crime,
    Fantasy,
    None
}

class BookShelfViewModel (
    private val bookShelfRepository: BookShelfRepository
):ViewModel(){
    var bookShelfUiState: BookShelfUiState by mutableStateOf(BookShelfUiState.Loading)

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    //Set current categoryType
    fun setCategory(categoryType: CategoryType) {
        _uiState.update { currentState ->
            currentState.copy(
                categoryType = categoryType,
            )
        }
    }

    //set currentBook
    fun setBook(book: Book) {
        _uiState.update { currentState ->
            currentState.copy(
                currentBook = book,
            )
        }
    }

    fun getBooks(){
        viewModelScope.launch {
            bookShelfUiState = BookShelfUiState.Loading
            bookShelfUiState = try{
                when(_uiState.value.categoryType){
                    CategoryType.Fiction -> BookShelfUiState.Success(bookShelfRepository.getFictionBooks())
                    CategoryType.Fantasy -> BookShelfUiState.Success(bookShelfRepository.getFantasyBooks())
                    CategoryType.Crime -> BookShelfUiState.Success(bookShelfRepository.getCrimeBooks())
                    CategoryType.Mystery -> BookShelfUiState.Success(bookShelfRepository.getMysteryBooks())
                    else -> BookShelfUiState.Error
                }
            }catch (e: IOException) {
                BookShelfUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookShelfApplication)
                val amphibiansRepository = application.container.bookShelfRepository
                BookShelfViewModel(bookShelfRepository = amphibiansRepository)
            }
        }
    }
}