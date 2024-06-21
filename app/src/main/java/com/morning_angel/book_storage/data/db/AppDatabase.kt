package com.morning_angel.book_storage.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.morning_angel.book_storage.data.db.dao.BookDao
import com.morning_angel.book_storage.data.db.entityes.Book

@Database(
    entities = [Book::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao() : BookDao
    companion object{
        fun build(context : Context, dbName : String) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
        }
    }
}