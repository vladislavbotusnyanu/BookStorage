package com.morning_angel.book_storage.app.ui.base.http_server

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morning_angel.book_storage.app.ui.screens.main.MainViewModel
import com.morning_angel.book_storage.app.ui.theme.BookStorageTheme
import com.morning_angel.book_storage.app.util.generateQRCode
import com.morning_angel.book_storage.app.util.getLocalIpAddress
import com.morning_angel.book_storage.app.util.toast
import com.morning_angel.book_storage.domain.http_server.MyHttpServer
import kotlinx.coroutines.delay


@Composable
fun ServerSettingsComposable(
    context: Context,
    clipboardManager: ClipboardManager,
    isHttpStarted: Boolean,
    vm: MainViewModel,
    paddingValues: PaddingValues
) {

    if (isHttpStarted) {
        var linkState by remember { mutableStateOf("http://" + context.getLocalIpAddress() + ":" + MyHttpServer.DEFAULT_PORT) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(10000)
                linkState =
                    "http://" + context.getLocalIpAddress() + ":" + MyHttpServer.DEFAULT_PORT
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
        ) {

            Row {
                Text(fontSize = 18.sp, text = linkState)
                Button(onClick = {
                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(linkState))
                    context.toast("Link copied")
                }) {
                    Text(fontSize = 18.sp, text = "Copy address")
                }
            }
            Image(
                bitmap = generateQRCode(linkState, 400).asImageBitmap(),
                contentDescription = null
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                if (isHttpStarted) vm.stopHttpServer()
                else vm.startHttpServer()
            }) {
            Text(text = if (isHttpStarted) "Stop server" else "Start server")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ServerPreview() {
    BookStorageTheme {
//        val context = LocalContext.current
//        val clipboardManager = LocalClipboardManager.current
//        ServerSettingsComposable(
//            context = context,
//            clipboardManager = clipboardManager,
//            paddingValues = paddingValues
//        )
    }
}