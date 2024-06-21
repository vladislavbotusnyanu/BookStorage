package com.morning_angel.book_storage.data.db.entityes

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.morning_angel.book_storage.data.ui.entityes.BookUI

@Entity(tableName = Book.TABLE_NAME, indices = [Index(value = ["title"], unique = true)])
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val filePath: String,
    val dateAdded: Long,
    val coverImagePath: String
){
    fun toBookUI() = BookUI(id, title, filePath, dateAdded, coverImagePath)
    companion object{
        val emptyBook = Book(0, "", "", 0, "")

        const val TABLE_NAME = "books_table"
    }
}