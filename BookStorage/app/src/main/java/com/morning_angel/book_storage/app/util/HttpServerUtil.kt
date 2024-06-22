package com.morning_angel.book_storage.app.util

import android.content.Context
import android.graphics.Bitmap
import android.net.wifi.WifiManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.net.InetAddress
import java.net.UnknownHostException

fun generateQRCode(content: String, size: Int): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(
                x,
                y,
                if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            )
        }
    }
    return bmp
}

fun Context.getLocalIpAddress(): String? {
    val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiInfo = wifiManager.connectionInfo
    val ipAddress = wifiInfo.ipAddress

    return try {
        val ipByteArray = byteArrayOf(
            (ipAddress and 0xFF).toByte(),
            (ipAddress shr 8 and 0xFF).toByte(),
            (ipAddress shr 16 and 0xFF).toByte(),
            (ipAddress shr 24 and 0xFF).toByte()
        )
        val inetAddress = InetAddress.getByAddress(ipByteArray)
        inetAddress.hostAddress
    } catch (e: UnknownHostException) {
        null
    }
}
