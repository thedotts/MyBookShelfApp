package com.example.bookshelfapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookItems(
    val totalItems: Int,
    val items: List<Book>
)

@Serializable
data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val categories: List<String>
)

@Serializable
data class ImageLinks(
    @SerialName(value = "smallThumbnail")
    val smallThumbnailSrc: String,
    @SerialName(value = "thumbnail")
    val thumbnailSrc: String
)
