package com.example.bookshelfapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.bookshelfapp.ui.screens.BookShelfViewModel
import com.example.bookshelfapp.ui.screens.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookshelfapp.R
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookshelfapp.ui.screens.BookScreen
import com.example.bookshelfapp.ui.screens.CategoryScreen
import com.example.bookshelfapp.ui.screens.CategoryType

enum class BookShelfScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    Category(title = R.string.categories),
    BookInfo(title = R.string.bookinfo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfApp(
    navController: NavHostController = rememberNavController()
){
    // Create bookShelfViewModel
    val bookShelfViewModel: BookShelfViewModel =
        viewModel(factory = BookShelfViewModel.Factory)

    //get book state
    val uiState by bookShelfViewModel.uiState.collectAsState()

    //Create Controller and initialization
    //get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    //get the name of the current screen
    val currentScreen = BookShelfScreen.valueOf(
        backStackEntry?.destination?.route?: BookShelfScreen.Start.name
    )
    val currentScreenTitle = if(currentScreen == BookShelfScreen.Category){
        uiState.categoryType.toString()
    }else{
        stringResource(currentScreen.title)
    }

    Scaffold(
        topBar = { BookShelfAppBar(
            currentScreenTitle = currentScreenTitle,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = {navController.navigateUp()},
            modifier = Modifier
        )}
    ) {innerPadding ->
        //Navigation host
        NavHost(
            navController = navController,
            startDestination = BookShelfScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = BookShelfScreen.Start.name){
                HomeScreen(
                    getBookAction = bookShelfViewModel::getBooks,
                    onButtonClicked = {navController.navigate(BookShelfScreen.Category.name)},
                    onCrimeButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Crime)},
                    onFantasyButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Fantasy)},
                    onFictionButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Fiction)},
                    onMysteryButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Mystery)},
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = BookShelfScreen.Category.name){
                CategoryScreen(
                    bookShelfUiState = bookShelfViewModel.bookShelfUiState,
                    onButtonClicked = {navController.navigate(BookShelfScreen.BookInfo.name)},
                    setBookAction = bookShelfViewModel::setBook,
                    retryAction = bookShelfViewModel::getBooks,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = BookShelfScreen.BookInfo.name){
                BookScreen(
                    book = uiState.currentBook,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfAppBar(
    modifier: Modifier = Modifier,
    currentScreenTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
){
    TopAppBar(
        title = { Text(
            text = currentScreenTitle,
            style = MaterialTheme.typography.headlineSmall
        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
