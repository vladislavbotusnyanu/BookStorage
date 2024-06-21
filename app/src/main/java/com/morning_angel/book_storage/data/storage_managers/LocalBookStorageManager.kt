package com.morning_angel.book_storage.data.storage_managers

import android.content.Context
import android.net.Uri
import com.morning_angel.book_storage.app.util.getDocumentFileName
import com.morning_angel.book_storage.data.storage_managers.base.FileStorageManager
import java.io.File
import java.io.FileOutputStream

class LocalBookStorageManager(private val context: Context) :
    FileStorageManager(context, STORAGE_DIR_NAME) {

    sealed class FilePuttingStatus {
        data class FilePuttingSuccess(val newFile: File) : FilePuttingStatus()
        data class FilePuttingFail(val errorMessage: String) : FilePuttingStatus()
    }

    fun deleteFile(file : File){
        checkDirExistsAndCreate()
        if(file.exists()) file.delete()
    }
    
    fun putFile(uri: Uri): FilePuttingStatus {
        checkDirExistsAndCreate()
        val uriFullFileName = context.getDocumentFileName(uri)
        val newFile = File(storageDir.absolutePath + File.separator + uriFullFileName)

        return context.createFileFromUri(uri, newFile)
    }

    private fun Context.createFileFromUri(uri: Uri, copiedFile: File): FilePuttingStatus {
        val inputStream =
            contentResolver.openInputStream(uri) ?: return FilePuttingStatus.FilePuttingFail(
                "Cannot create openInputStream with uri: $uri"
            )
        val byteArray = ByteArray(1024)
        var bytesRead: Int

        if(copiedFile.exists()) copiedFile.delete() //overwriting a file if there is the same
        val outputStream = FileOutputStream(copiedFile)
        while (inputStream.read(byteArray).also { bytesRead = it } != -1) {
            outputStream.write(byteArray, 0, bytesRead)
        }

        inputStream.close()
        outputStream.close()

        return FilePuttingStatus.FilePuttingSuccess(copiedFile)
    }

    companion object {
        const val STORAGE_DIR_NAME = "LocalBookStorage"
    }
}