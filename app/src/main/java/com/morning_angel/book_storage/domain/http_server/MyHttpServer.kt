package com.morning_angel.book_storage.domain.http_server

import android.content.Context
import com.morning_angel.book_storage.data.ui.entityes.BookUI
import fi.iki.elonen.NanoHTTPD

class MyHttpServer(bookList : List<BookUI>, port: Int, private val context: Context) : NanoHTTPD(port) {

    fun start(daemon : Boolean){
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, daemon) //todo check daemon
    }

    override fun serve(session: IHTTPSession?): Response {
        val msg = """
            <html>
                <body>
                    <h1>Hello, Android HTTP Server!</h1>
                    <p>This is a minimal HTTP server running on your Android device.</p>
                </body>
            </html>
            """.trimIndent()
        return newFixedLengthResponse(msg)
    }
}
