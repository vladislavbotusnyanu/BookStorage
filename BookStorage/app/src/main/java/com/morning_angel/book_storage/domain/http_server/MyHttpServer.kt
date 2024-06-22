package com.morning_angel.book_storage.domain.http_server

import com.morning_angel.book_storage.data.ui.entityes.BookUI
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.Response.IStatus
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.logging.Level
import java.util.logging.Logger


class MyHttpServer(
    port: Int = DEFAULT_PORT
) : NanoHTTPD(port) {

    var bookList: List<BookUI> = emptyList()
    private val response get() = run {
        var msg = """
            <html>
                <body>
                    <h1>Book Storage</h1>
                   <hr><ul>
                   """.trimIndent()


        bookList.forEach {
            msg += """<li><a href=${it.filePath}>${it.title}</a></li>""".trimIndent()
        }

        msg += """
                    </ul><hr>
                </body>
            </html>
            """.trimIndent()
        msg
    }
    fun start(daemon: Boolean) {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, daemon)
    }
    override fun serve(session: IHTTPSession?): Response {

        println("serverHTD " + session?.uri)


        if (session?.uri?.contains("/data/data/com.morning_angel.book_storage/files/LocalBookStorage") == true) {
            return downloadFile(File(session.uri))
        }

        return newFixedLengthResponse(response)
    }

    private fun downloadFile(file: File): Response {
        return try {

            val fis = FileInputStream(file)

            getFixedLengthResponse(
                Response.Status.OK,
                "application/octet-stream",
                fis,
                file.length()
            )
        } catch (ex: FileNotFoundException) {
            Logger.getLogger(MyHttpServer::class.java.getName()).log(Level.SEVERE, null, ex)
            newFixedLengthResponse(response)
        }
    }

    fun getFixedLengthResponse(
        status: IStatus,
        mimeType: String,
        data: InputStream,
        totalBytes: Long
    ): Response {
        val response: Response = newFixedLengthResponse(status, mimeType, data, totalBytes)
        response.addHeader("Accept-Ranges", "bytes")
        return response
    }

    companion object {
        const val DEFAULT_PORT = 8080
    }
}
