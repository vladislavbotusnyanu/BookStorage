package com.morning_angel.book_storage.data.ui.navigation

import com.morning_angel.book_storage.data.ui.entityes.BookUI

sealed class Screens{
    data object BookList : Screens()
    data class ServerSettings(val bookList: List<BookUI> = emptyList()) : Screens()
}
