package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import kotlin.reflect.KFunction1

@Composable
fun CategoryScreen(
    bookShelfUiState: BookShelfUiState,
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Book, Unit>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    when (bookShelfUiState) {
        is BookShelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookShelfUiState.Success -> BookList(
            books = bookShelfUiState.books.items,
            onButtonClicked = onButtonClicked,
            setBookAction = setBookAction,
            modifier = Modifier.fillMaxWidth()
        )
        is BookShelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun BookList(
    books: List<Book>,
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Book, Unit>,
    modifier: Modifier = Modifier
){
    if (books.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .wrapContentHeight(),
            contentPadding = PaddingValues(4.dp)
        ){
            items(books){
                BookItem(
                    book = it,
                    onButtonClicked = onButtonClicked,
                    setBookAction = setBookAction,
                    modifier.padding(1.dp)
                )
            }
        }
    }

}
@Composable
fun BookItem(
    book: Book,
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Book, Unit>,
    modifier: Modifier = Modifier
){
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                setBookAction.invoke(book)
                onButtonClicked.invoke()
            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ){
            BookImage(book.volumeInfo.imageLinks.smallThumbnailSrc, book.volumeInfo.title, modifier = Modifier)
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 10.dp)
            )
        }

    }
}

@Composable
fun BookImage(imageSrc: String, title:String, modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imageSrc.replace("http","https"))
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            contentDescription = title,
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
        )
    }
}

