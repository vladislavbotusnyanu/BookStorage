package com.morning_angel.book_storage.domain.repo.db.book

import android.content.Context
import com.morning_angel.book_storage.data.db.AppDatabase
import com.morning_angel.book_storage.data.db.entityes.Book
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BookRepo {
    private val database = AppDatabase.build(context, DB_BOOK_NAME)
    private val bookDao = database.bookDao()

    override suspend fun getAllBooks(): List<Book> = bookDao.getAllBooks()

    override suspend fun searchBook(bookName: String): List<Book> =
        bookDao.searchBooks(bookName)

    override suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    override suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    companion object{
        const val DB_BOOK_NAME = "book_db"
    }
}