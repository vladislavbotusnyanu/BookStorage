package com.morning_angel.book_storage

import android.content.Context
import com.morning_angel.book_storage.data.storage_managers.LocalBookStorageManager
import com.morning_angel.book_storage.domain.http_server.MyHttpServer
import com.morning_angel.book_storage.domain.repo.db.book.BookRepo
import com.morning_angel.book_storage.domain.repo.db.book.BookRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpServer(
    ) : MyHttpServer = MyHttpServer()

    @Provides
    @Singleton
    fun provideBookRepo(
        @ApplicationContext context: Context
    ) : BookRepo = BookRepoImpl(context)

    @Provides
    @Singleton
    fun provideLocalBookStorageManager(
        @ApplicationContext context: Context
    ) : LocalBookStorageManager = LocalBookStorageManager(context)


}