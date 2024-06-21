package com.morning_angel.book_storage.app.ui.screens.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.morning_angel.book_storage.app.ui.base.main.BookListTopBar
import com.morning_angel.book_storage.app.ui.base.main.FloatingButton
import com.morning_angel.book_storage.app.ui.base.main.MainNavDrawer
import com.morning_angel.book_storage.app.ui.base.main.SavedBooksList
import com.morning_angel.book_storage.app.ui.screens.add_book.AddBookActivity
import com.morning_angel.book_storage.app.ui.theme.BookStorageTheme
import com.morning_angel.book_storage.app.util.openBookByDefaultApp
import com.morning_angel.book_storage.app.util.shareFile
import com.morning_angel.book_storage.app.util.startActivity
import com.morning_angel.book_storage.data.db.entityes.Book
import com.morning_angel.book_storage.data.ui.entityes.NavItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var vm: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookStorageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    vm = viewModel()
                    vm.getAllBooks()
                    MainComposable(vm)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume(); if (::vm.isInitialized) vm.getAllBooks()
    }
}

@Composable
fun MainComposable(
    vm: MainViewModel,
    context: Context = LocalContext.current
) {
    val state = vm.viewState.observeAsState(MainViewStates())
    val books = state.value.allBookList
    var bookSelectedItem by remember { mutableStateOf(Book.emptyBook) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navDrawList = listOf(
        NavItem(Icons.Filled.List, "Book List", true, {}),
        NavItem(Icons.Filled.Settings, "Server settings", false, {}),
    )

    val changedMap = books.toMutableList()

    MainNavDrawer(navItems = navDrawList, drawerState = drawerState, onClick = {}) {
        BookListTopBar(
            onNavButtonClick = {
                scope.launch { drawerState.apply { if (isClosed) open() else close() } }
            }
        ) { paddingValues ->

            SavedBooksList(
                allBookList = state.value.allBookList,
                paddingValues = paddingValues,
                onClick = { context.openBookByDefaultApp(File(it.filePath)) },
                onLongClick = { vm.setStateExpanded(it ) },
                onDeleteClick = { vm.deleteBook(it.toBook()) },
                onShareClick = { context.shareFile(File(it.filePath)) },
                LocalDensity.current
            )
        }
        FloatingButton(
            "Add book",
            Icons.Filled.Add
        ) { context.startActivity(AddBookActivity::class) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookStorageTheme {
        Row {
            Column {
                Text(text = "duwal")
                Text(text = "duwal1")
            }
            Column {
                Text(text = "duwal")
                Text(text = "duwal1")
            }
        }

//        MainComposable(
//            listOf(
//                Book(
//                    0,
//                    "title0",
//                    "path0",
//                    12312L,
//                    "https://marketplace.canva.com/EAFPHUaBrFc/1/0/1003w/canva-black-and-white-modern-alone-story-book-cover-QHBKwQnsgzs.jpg"
//                ),
//                Book(
//                    1,
//                    "title1",
//                    "path1",
//                    12312L,
//                    "https://marketplace.canva.com/EAFPHUaBrFc/1/0/1003w/canva-black-and-white-modern-alone-story-book-cover-QHBKwQnsgzs.jpg"
//                ),
//                Book(
//                    2,
//                    "title2",
//                    "path2",
//                    12312L,
//                    "https://marketplace.canva.com/EAFPHUaBrFc/1/0/1003w/canva-black-and-white-modern-alone-story-book-cover-QHBKwQnsgzs.jpg"
//                ),
//                Book(
//                    3,
//                    "title3",
//                    "path3",
//                    12312L,
//                    "https://marketplace.canva.com/EAFPHUaBrFc/1/0/1003w/canva-black-and-white-modern-alone-story-book-cover-QHBKwQnsgzs.jpg"
//                ),
//            )
//        )
    }
}