package com.morning_angel.book_storage.app.ui.screens.add_book

import com.morning_angel.book_storage.app.base.view_model.BaseViewState

sealed class AddBookStates(
    val isBookAdded : Boolean = false,
    val error : String = ""
) : BaseViewState {
    data object defaultState : AddBookStates(isBookAdded = false, error = "")
    data object onBookAddedSuccessState : AddBookStates(isBookAdded = true)
    data class onShowErrorState(val errorMessage: String) : AddBookStates(error = errorMessage)
}