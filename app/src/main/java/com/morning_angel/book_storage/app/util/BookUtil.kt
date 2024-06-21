package com.morning_angel.book_storage.app.util

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.morning_angel.book_storage.mimeTypeEpub
import com.morning_angel.book_storage.mimeTypeFb2
import com.morning_angel.book_storage.mimeTypeFb2Zip
import com.morning_angel.book_storage.mimeTypeHtml
import com.morning_angel.book_storage.mimeTypePdf
import com.morning_angel.book_storage.mimeTypeTxt
import java.io.File
import java.sql.Date
import java.text.SimpleDateFormat


fun getBookMimeType(file: File): String? {
    val extension = file.extension
    return when (extension.lowercase()) {
        "pdf" -> mimeTypePdf
        "epub" -> mimeTypeEpub
        "fb2" -> mimeTypeFb2
        "fb2.zip" -> mimeTypeFb2Zip
        "html" -> mimeTypeHtml
        "txt" -> mimeTypeTxt
        else -> null
    }
}

@Composable
fun getBookPreviewImage(file: File): Painter {
    if (file.exists())
        when (file.extension) {
            "pdf" -> {
                val pdfRenderer = PdfRenderer(ParcelFileDescriptor.open(file, MODE_READ_ONLY))
                val previewPage = pdfRenderer.openPage(0)
                val bitmapPreviewPage =
                    Bitmap.createBitmap(
                        previewPage.width,
                        previewPage.height,
                        Bitmap.Config.ARGB_8888
                    )
                previewPage.render(bitmapPreviewPage, null, null, RENDER_MODE_FOR_DISPLAY)
                previewPage.close()
                pdfRenderer.close()


                return BitmapPainter(Bitmap.createBitmap(bitmapPreviewPage).asImageBitmap())
//            bitmapPreviewPage
            }

            else -> return painterResource(android.R.drawable.ic_menu_help)
//        "epub" -> { }
//        "fb2" -> { }
        }
    else return painterResource(android.R.drawable.ic_menu_help)
}


fun getAddedTime(timestamp: Long): String {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val instant = Instant.ofEpochSecond(timestamp)
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        formatter.format(instant.atZone(ZoneId.systemDefault()))
//    } else {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return formatter.format(date)
//    }
}