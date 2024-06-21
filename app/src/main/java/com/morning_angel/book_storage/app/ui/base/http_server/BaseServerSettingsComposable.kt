package com.morning_angel.book_storage.app.ui.base.http_server

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.morning_angel.book_storage.app.ui.theme.BookStorageTheme
import com.morning_angel.book_storage.app.util.getLocalIpAddress


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookStorageTheme {
        val text = LocalContext.current.getLocalIpAddress().toString()
        println("wdkajgduh $text")
        Text(text = text)
    }
}