package com.example.e_book.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object Home

    @Serializable
    data class PdfViewer(
        val pdfUrl: String
    )

    @Serializable
    data class BookByCategory(
        val category: String
    )

    @Serializable
    object BookMark
}