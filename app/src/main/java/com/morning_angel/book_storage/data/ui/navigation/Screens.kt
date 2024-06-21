package com.morning_angel.book_storage.data.ui.navigation

sealed class Screens(val route : String){
    data object BookList : Screens("BookList")
    data object ServerSettings : Screens("ServerSettings")
}
