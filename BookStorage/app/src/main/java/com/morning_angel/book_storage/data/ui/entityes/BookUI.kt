package com.morning_angel.book_storage.data.ui.entityes

import com.morning_angel.book_storage.data.db.entityes.Book

data class BookUI(
    val id : Int,
    val title: String,
    val filePath: String,
    val dateAdded: Long,
    val coverImagePath: String,
    val expanded: Boolean = false
) {
    fun toBook() = Book(id = id, title = title, filePath= filePath, dateAdded =dateAdded, coverImagePath = coverImagePath)
}