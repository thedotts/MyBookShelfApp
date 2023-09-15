package com.example.bookshelfapp.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R

import com.example.bookshelfapp.ui.theme.BookshelfAppTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
    //bookShelfUiState: BookShelfUiState, retryAction: () -> Unit, modifier: Modifier = Modifier
) {
    BookShelfScreen(
            modifier = modifier.fillMaxSize()
        )
//    when (bookShelfUiState) {
//        is BookShelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
//        is BookShelfUiState.Success -> BookShelfScreen(
//            modifier = modifier.fillMaxSize()
//        )
//
//        is BookShelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
//    }
}
/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BookShelfScreen(modifier: Modifier = Modifier){

    Column(modifier = modifier) {
        CategoryBox({},R.drawable.fictionimage,"Fiction", Modifier.padding(5.dp))
        CategoryBox({},R.drawable.misteryimage,"Mystery", Modifier.padding(5.dp))
        CategoryBox({},R.drawable.crimeimage,"Crime", Modifier.padding(5.dp))
        CategoryBox({},R.drawable.fantasyimage,"Fantasy", Modifier.padding(5.dp))
    }
}

@Composable
fun CategoryBox(onItemClick: () -> Unit, @DrawableRes imageRes:Int, category: String, modifier: Modifier = Modifier){
    Box(
        modifier = modifier
        .fillMaxWidth()
        .clickable {
                onItemClick.invoke()
                Log.d("CategoryBox", "Clicked")

            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Image(
                painter = painterResource(imageRes),
                contentDescription = stringResource(R.string.loading),
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
            Text(
                text = category,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun BookShelfCardPreview(){
    BookshelfAppTheme {
        CategoryBox({},R.drawable.fictionimage,category = "Fiction", Modifier.padding(5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BookShelfPreview(){
    BookshelfAppTheme {
        BookShelfScreen()
    }
}