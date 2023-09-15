package com.example.bookshelfapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookshelfapp.model.Categories
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
        topBar = { BookShelfAppBar(scrollBehavior = scrollBehavior)}
    ) {innerPadding ->

        //Navigation host
        NavHost(
            navController = navController,
            startDestination = BookShelfScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = BookShelfScreen.Start.name){
                HomeScreen(
                    modifier = Modifier
                )
            }

            composable(route = BookShelfScreen.Category.name){
                CategoryScreen(
                    categories = CategoryType.values(),
                    bookShelfUiState = bookShelfViewModel.bookShelfUiState,
                    bookUiState = uiState,
                    retryAction = bookShelfViewModel::getBooks,
                    onFictionButtonClicked = {},
                    onMysteryButtonClicked = {},
                    onCrimeButtonClicked =  {},
                    onFantasyButtonClicked =  {},
                    modifier = Modifier
                )
            }

            composable(route = BookShelfScreen.BookInfo.name){
                BookScreen(
                    book = uiState.currentBook!!,
                    modifier = Modifier
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "My Book Shelf",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier
    )
}