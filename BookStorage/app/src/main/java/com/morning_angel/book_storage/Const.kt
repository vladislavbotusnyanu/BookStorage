package com.morning_angel.book_storage

const val mimeTypePdf = "application/pdf"
const val mimeTypeEpub = "application/epub+zip"
const val mimeTypeFb2 = "application/fb2+xml"
const val mimeTypeFb2Zip = "application/zip"
const val mimeTypeHtml = "text/html"
const val mimeTypeTxt = "text/plain"

val supportedMimeTypes = arrayOf(
    mimeTypePdf,             // PDF
    mimeTypeEpub,        // EPUB
    mimeTypeFb2,          // FB2
    mimeTypeFb2Zip,             // FB2.zip (compressed FB2) - requires checking file content
    mimeTypeHtml,                    // HTML
    mimeTypeTxt,                   // TXT
//    "image/vnd.djvu",              // DjVu (may require additional libraries for proper handling)
//    "application/x-mobipocket-ebook", // MOBI
//    "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // DOCX
//    "text/rtf",                     // RTF
//    "application/vnd.ms-htmlhelp", // CHM
//    "application/x-cbz",            // CBZ
//    "application/x-cbr",            // CBR
//    "application/x-cbr",            // CBT (often uses CBR MIME type)
)
