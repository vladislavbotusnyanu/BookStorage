package com.morning_angel.book_storage.app.ui.screens.main

import com.morning_angel.book_storage.app.base.view_model.BaseViewState
import com.morning_angel.book_storage.data.ui.entityes.BookUI

data class MainViewStates(
    val allBookList : List<BookUI> = emptyList(),
    val searchedBookList : List<BookUI> = emptyList(),
    val isHttpServerStarted : Boolean = false
) : BaseViewState