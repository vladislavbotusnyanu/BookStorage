package com.morning_angel.book_storage.app.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import java.io.File
import kotlin.reflect.KClass


inline val Context.authority get() = "$packageName.fileprovider"

private val uriPermission
    get() = Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION

fun Context.uriForFile(file: File): Uri = FileProvider.getUriForFile(this, authority, file)

fun Context.shareFileIntent(uri: Uri): Intent = Intent
    .createChooser(intentActionSendFile(uri), "Share File")

fun Context.intentActionSendFile(uri: Uri): Intent = Intent(Intent.ACTION_SEND)
    .setType(contentResolver.getType(uri))
    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    .putExtra(Intent.EXTRA_STREAM, uri)

fun Context.queryIntentActivities(uri: Uri, intent: Intent) = packageManager
    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    .forEach { grantUriPermission(it.activityInfo.packageName, uri, uriPermission) }

const val LOG_FILE = "logcat.txt"

fun Context.startLoggingIntoLogFile() {
    val pathname = filesDir.path + File.separator + LOG_FILE
    File(pathname).apply { if (!this.exists()) this.createNewFile() }
    Runtime.getRuntime()
        .exec(arrayOf("logcat", "-f", pathname, applicationContext.packageName))
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.shareLogFile() {
    val pathname = filesDir.path + File.separator + LOG_FILE
    val file = File(pathname).apply {
        if (!this.exists()) {
            toast("Log file is not exists there is nothing to share")
            return
        }
    }
    val uri = uriForFile(file)
    val intent = shareFileIntent(uri)
    queryIntentActivities(uri, intent)
    startActivity(intent)
}

fun Context.shareFile(file: File){
    if(file.exists()){
        val uri = uriForFile(file)
        val intent = shareFileIntent(uri)
        queryIntentActivities(uri, intent)
        startActivity(intent)
    } else toast(file.name + " is not exist")
}


fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}

inline val Context.downloadManager get() = getSystemService(DownloadManager::class.java)
inline val Context.activityManager get() = getSystemService(ActivityManager::class.java)

fun Context.isActivityRunning(activityClass: KClass<*>): Boolean {

    if (Build.VERSION.SDK_INT >= 23) {
        val tasks = activityManager.appTasks
        for (task in tasks) {
            if (activityClass.java.canonicalName.equals(
                    task.taskInfo.baseActivity?.className,
                    ignoreCase = true
                )
            )
                return true
        }
    } else {
        @Suppress("DEPRECATION") val tasks = activityManager.getRunningTasks(Integer.MAX_VALUE)
        for (task in tasks) {
            if (activityClass.java.equals(task.baseActivity?.className))
                return true
        }
    }

    return false
}

fun Context.startActivity(kclass: KClass<*>) {
    startActivity(
        Intent(
            this,
            kclass.java
        )
    )
}

fun Context.getDocumentFileName(uri: Uri): String {
    return this.getDocumentFromUri(uri).name
        ?: throw Throwable(message = "Wrong DocumentFile, DocumentFile.name is null, cannot get DocumentFile.name")
}

fun Context.getDocumentFromUri(uri: Uri): DocumentFile {
    return DocumentFile.fromSingleUri(this, uri)
        ?: throw Throwable(message = "Wrong uri, DocumentFile is null, cannot get DocumentFile")
}

fun Context.openBookByDefaultApp(file: File) {
    if (file.exists()) {
        val mimeType = getBookMimeType(file)
        if (mimeType != null) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                val fileUri: Uri = FileProvider.getUriForFile(this@openBookByDefaultApp, authority, file)
                setDataAndType(fileUri, mimeType)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            if (intent.resolveActivity(packageManager) != null) startActivity(intent)
            else toast("There is no any app which can open this file")
        } else toast("Cannot define file MIME-type.")
    } else toast("File not found.")

}