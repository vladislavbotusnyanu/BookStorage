package com.morning_angel.book_storage.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.morning_angel.book_storage.data.db.entityes.Book

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book : Book)
    @Delete
    fun deleteBook(book : Book)

    @Query("SELECT * FROM ${Book.TABLE_NAME}")
    fun getAllBooks() : List<Book>
    @Query("""
        SELECT * FROM ${Book.TABLE_NAME}
        WHERE title LIKE :title
        AND filePath LIKE :filePath
        AND dateAdded = :dateAdded
    """)
    suspend fun searchBooks(
        title: String,
        filePath: String,
        dateAdded: Long
    ): List<Book>
}