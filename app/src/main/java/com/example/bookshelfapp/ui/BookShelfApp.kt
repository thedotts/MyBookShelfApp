package com.example.bookshelfapp.ui

import android.util.Log
import androidx.annotation.StringRes
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
    //Create Controller and initialization
    //get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    //get the name of the current screen
    val currentScreen = BookShelfScreen.valueOf(
        backStackEntry?.destination?.route?: BookShelfScreen.Start.name
    )

    // Create bookShelfViewModel
    val bookShelfViewModel: BookShelfViewModel =
        viewModel(factory = BookShelfViewModel.Factory)

    //get book state
    val uiState by bookShelfViewModel.uiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookShelfAppBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = {navController.navigateUp()}
        )}
    ) {innerPadding ->

        //Navigation host
        NavHost(
            navController = navController,
            startDestination = BookShelfScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = BookShelfScreen.Start.name){
                Log.d("BookShelfAppScreen","Start is called")
                HomeScreen(
                    getBookAction = bookShelfViewModel::getBooks,
                    onButtonClicked = {navController.navigate(BookShelfScreen.Category.name)},
                    onCrimeButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Crime)},
                    onFantasyButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Fantasy)},
                    onFictionButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Fiction)},
                    onMysteryButtonClicked = {bookShelfViewModel.setCategory(CategoryType.Mystery)},
                    modifier = Modifier
                )
            }

            composable(route = BookShelfScreen.Category.name){
                Log.d("BookShelfAppScreen","Category is called")
                CategoryScreen(
                    bookShelfUiState = bookShelfViewModel.bookShelfUiState,
                    bookUiState = uiState,
                    onButtonClicked = {navController.navigate(BookShelfScreen.BookInfo.name)},
                    setBookAction = bookShelfViewModel::setBook,
                    retryAction = bookShelfViewModel::getBooks,
                    modifier = Modifier
                )
            }

            composable(route = BookShelfScreen.BookInfo.name){
                BookScreen(
                    book = uiState.currentBook,

                    modifier = Modifier
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfAppBar(
    currentScreen: BookShelfScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
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
