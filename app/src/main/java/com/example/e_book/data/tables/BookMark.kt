package com.example.e_book.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_table")
data class BookMark(
    @PrimaryKey(autoGenerate = true) val id: Int? =null,
    val bookAuthor:String,
    val bookImage:String,
    val bookUrl:String,
    val booksName:String,
//    val pageCount: Int,
    val filePath: String,
    val isBookmarked: Boolean = false

)
