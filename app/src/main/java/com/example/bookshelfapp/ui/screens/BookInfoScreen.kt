package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun BookScreen(
    book: Book?,
    modifier: Modifier = Modifier){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()

    ){
        ImageContainer(book!!.volumeInfo.imageLinks.thumbnailSrc, book.volumeInfo.title, modifier = Modifier
            .padding(15.dp)
            .weight(1f))
        Spacer(modifier = Modifier.padding(top = 5.dp))
        BookDetail(book, modifier = Modifier
            .padding(15.dp)
            .weight(2f))
    }
}

@Composable
fun ImageContainer(imageSrc: String, title:String, modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imageSrc.replace("http","https"))
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Fit,
            contentDescription = title,
            modifier = Modifier
                .fillMaxHeight()
        )
    }
}

@Composable
fun BookDetail( book: Book, modifier: Modifier = Modifier){
    Box (modifier = modifier
        .verticalScroll(rememberScrollState())
    ){
        Column (){
            Text(
                text = "${book.volumeInfo.title} (${book.volumeInfo.authors.joinToString(",")})",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = book.volumeInfo.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BookScreenPreview(){
    BookshelfAppTheme {
        Surface {
            val book = Book(
                id="test",
                volumeInfo = VolumeInfo(
                    title = "test",
                    authors = listOf("test",""),
                    description = "Category theory reveals commonalities between structures of all sorts. This book shows its potential in science, engineering, and beyond.",
                    imageLinks = ImageLinks(
                        smallThumbnailSrc = "",
                        thumbnailSrc = "http://books.google.com/books/content?id=IjGdDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                    )
                )
            )
            BookScreen(book = book)
        }
    }
}