package com.morning_angel.book_storage.app.base.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morning_angel.book_storage.domain.repo.db.book.BookRepo

abstract class BaseViewModel<T : BaseViewState>(defaultState: T) : ViewModel() {

    protected lateinit var repo : BookRepo

    protected val _viewState = MutableLiveData(defaultState)
    val viewState: LiveData<T> = _viewState

    protected fun setViewState(viewState: T?){
        _viewState.postValue(viewState)
    }
}