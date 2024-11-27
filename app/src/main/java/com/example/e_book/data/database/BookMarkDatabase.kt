package com.example.e_book.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_book.data.tables.BookMark
import com.example.e_book.data.tables.BookMarkDao

@Database(entities = [BookMark::class], version = 3, exportSchema = false)
abstract class BookMarkDatabase: RoomDatabase() {
    abstract fun getBookMarkDao(): BookMarkDao
}