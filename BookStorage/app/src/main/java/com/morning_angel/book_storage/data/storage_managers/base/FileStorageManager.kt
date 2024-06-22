package com.morning_angel.book_storage.data.storage_managers.base

import android.content.Context
import android.util.Log
import java.io.File

abstract class FileStorageManager(
    context: Context,
    storageDirName: String,
) {

    var storageDir: File = File(context.filesDir.canonicalPath + File.separator + storageDirName)
        protected set

    open fun checkDirExistsAndCreate() {
        if (!storageDir.exists()) storageDir.mkdir()
    }

    protected open fun putFile(input: File, target: File, overwrite: Boolean = false) {
        checkDirExistsAndCreate()
        if(input.canonicalPath == target.canonicalPath) return
        input.copyTo(target, overwrite)
    }

    open fun putFile(input: File) = putFile(
        input, File(storageDir.absolutePath + File.separator + input.name)
    )
    protected open fun getFile(path : String) : File? {
        checkDirExistsAndCreate()
        val savedFile = File(path)

        return if (savedFile.exists()) return savedFile
        else null
    }

    open fun getAllFilesInStorage(): List<File> {
        return storageDir.listFiles()?.toList()?.sortedBy { it.lastModified() } ?: emptyList()
    }

    open fun clearDir(onFinished: (() -> Unit)? = null) {
        getAllFilesInStorage().forEach {
            it.delete()
        }
        onFinished?.invoke()
    }

    open fun logAllFilesInDir(logTitle: String) {
        if (storageDir.isDirectory) getAllFilesInStorage().forEach {
            Log.d(logTitle, it.absolutePath)
        }
        else Log.e(
            logTitle, "storageDir.isDirectory == ${storageDir.isDirectory} \n" +
                    "\tstorageDir.isFile == ${storageDir.isFile}\n" +
                    "\t\t||| It's not a directory"
        )
    }

    open fun printAllFilesInDir(printTitle: String) {
        if (storageDir.isDirectory) getAllFilesInStorage().forEach {
            println(printTitle + " " + it.absolutePath)
        }
        else print(
            printTitle + " storageDir.isDirectory == ${storageDir.isDirectory} \n" +
                    "\tstorageDir.isFile == ${storageDir.isFile}\n" +
                    "\t\t||| It's not a directory"
        )
    }

}