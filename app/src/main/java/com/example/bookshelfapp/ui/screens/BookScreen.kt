package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
fun BookScreen(book: Book, modifier: Modifier = Modifier){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ){
        ImageContainer(book.volumeInfo.imageLinks.thumbnailSrc, book.volumeInfo.title, modifier = modifier.padding(15.dp))
        Spacer(modifier = Modifier.padding(top = 5.dp))
        BookDetail(book, modifier = modifier.padding(15.dp))
    }
}

@Composable
fun ImageContainer(imageSrc: String, title:String, modifier: Modifier = Modifier){
    Box(modifier){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imageSrc)
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Composable
fun BookDetail( book: Book, modifier: Modifier = Modifier){
    Column (modifier = modifier){
        Text(
            text = "${book.volumeInfo.title} (${book.volumeInfo.authors.joinToString(",")})",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = book.volumeInfo.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun BookScreenPreview(){
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
        BookScreen(fakedata)
    }
}