package com.morning_angel.book_storage.app.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.morning_angel.book_storage.app.base.view_model.BaseViewModel
import com.morning_angel.book_storage.data.db.entityes.Book
import com.morning_angel.book_storage.data.storage_managers.LocalBookStorageManager
import com.morning_angel.book_storage.data.ui.entityes.BookUI
import com.morning_angel.book_storage.domain.repo.db.book.BookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookRepo : BookRepo,
    private val bookStorageManager: LocalBookStorageManager
) : BaseViewModel<MainViewStates>(MainViewStates()) {

    fun setNewList(list : List<BookUI>){
        _viewState.value = viewState.value?.copy(allBookList = list)
    }
    fun getAllBooks() = viewModelScope
        .launch(Dispatchers.IO) {
            val books = bookRepo.getAllBooks()
            viewState.value?.let {
                setViewState(it.copy(allBookList = books.map { it.toBookUI() }))
            }
        }

    fun setStateExpanded(bookUI: BookUI){
        _viewState.value?.let {
            _viewState.value = it.copy(allBookList = it.allBookList.map { currentBookUI ->
                if(currentBookUI.id == bookUI.id) currentBookUI.copy(expanded = true)
                else currentBookUI.copy(expanded = false)
            })
        }
    }

    fun deleteBook(book : Book) = viewModelScope
        .launch(Dispatchers.IO) {
            bookRepo.deleteBook(book)
            bookStorageManager.deleteFile(File(book.filePath))

            viewState.value?.let { state ->
                setViewState(viewState = viewState.value?.copy(allBookList = state.allBookList.filter { it.id != book.id }))
            }
        }
}