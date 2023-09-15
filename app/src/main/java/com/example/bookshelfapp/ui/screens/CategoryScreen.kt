package com.example.bookshelfapp.ui.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.ImageLinks
import com.example.bookshelfapp.model.VolumeInfo
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun CategoryScreen(
                   bookShelfUiState: BookShelfUiState,
                   bookUiState:BookUiState,
                   retryAction: () -> Unit,
                   modifier: Modifier = Modifier
){
    Log.d("CategoryScreen",(bookShelfUiState == BookShelfUiState.Loading).toString() )
    when (bookShelfUiState) {
        is BookShelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookShelfUiState.Success -> BookList(
            books = bookShelfUiState.books.items,
            category = bookUiState.categoryType,
            modifier = Modifier.fillMaxSize()
        )
        is BookShelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }

}

@Composable
fun BookList(
    books: List<Book>,
    category:CategoryType,
    modifier: Modifier = Modifier
){
    Log.d("CategoryScreen","BookList is called")
    Log.d("CategoryScreen",category.name)
    Log.d("CategoryScreen","${books.size}")
//    Log.d("CategoryScreen","${books.toString()}")

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ){
        items(books){
            BookItem(
                book = it,
                categoryType = category,
                onButtonClicked = {}
            )
        }
    }
}
@Composable
fun BookItem(
    book: Book,
    categoryType: CategoryType,
    onButtonClicked: (CategoryType) -> Unit,
    modifier: Modifier = Modifier
){
//    Log.d("CategoryScreen","BookItem is called")
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onButtonClicked.invoke(categoryType)
                Log.d("BookItem", "Clicked")
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
                style = MaterialTheme.typography.bodyLarge,
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
        Log.d("CategoryScreen","$imageSrc")

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imageSrc.replace("http","https"))
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            contentDescription = title,
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview(){
    BookshelfAppTheme {
        var fakedata: Book = Book(
            id = "123",
            volumeInfo = VolumeInfo(
                title = "My Great Daddy",
                authors = listOf("Eriko","Hunter"),
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                imageLinks = ImageLinks("smallThunbnail", "thumbnail"),
                categories =listOf("Fiction")
            )
        )
        //CategoryScreen(fakedata)
    }
}