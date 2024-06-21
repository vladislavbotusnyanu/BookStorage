package com.morning_angel.book_storage.app.ui.screens.add_book

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.morning_angel.book_storage.app.base.view_model.BaseViewModel
import com.morning_angel.book_storage.data.db.entityes.Book
import com.morning_angel.book_storage.data.storage_managers.LocalBookStorageManager
import com.morning_angel.book_storage.data.storage_managers.LocalBookStorageManager.FilePuttingStatus.FilePuttingFail
import com.morning_angel.book_storage.data.storage_managers.LocalBookStorageManager.FilePuttingStatus.FilePuttingSuccess
import com.morning_angel.book_storage.domain.repo.db.book.BookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepo: BookRepo,
    private val bookStorageManager: LocalBookStorageManager
) : BaseViewModel<AddBookStates>(AddBookStates.defaultState) {

    fun addBookThroughLocalFiles(uri: Uri?) {
        if (uri != null) {
            val result = bookStorageManager.putFile(uri)
            when (result) {
                is FilePuttingSuccess -> insertBookToDatabase(result)
                is FilePuttingFail -> setViewState(AddBookStates.onShowErrorState(result.errorMessage))
            }
        } else setViewState(AddBookStates.onShowErrorState("selected file uri is null"))
    }

    private fun insertBookToDatabase(result : FilePuttingSuccess){
        viewModelScope.launch(Dispatchers.IO) {
            bookRepo.insertBook(
                Book(
                    title = result.newFile.name,
                    filePath = result.newFile.canonicalPath,
                    dateAdded = result.newFile.lastModified(),
                    coverImagePath = ""
                )
            )
            setViewState(AddBookStates.onBookAddedSuccessState)
        }
    }

    fun addBookThroughFlibustaClearNet() {

    }

    fun addBookThroughFlibustaI2pNet() {

    }

}