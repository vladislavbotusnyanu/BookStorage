package com.morning_angel.book_storage.domain.repo.db.book

import com.morning_angel.book_storage.data.db.entityes.Book

interface BookRepo {
    suspend fun getAllBooks() : List<Book>
    suspend fun searchBook(bookName : String) : List<Book>
    suspend fun insertBook(book: Book)
    suspend fun deleteBook(book: Book)
}