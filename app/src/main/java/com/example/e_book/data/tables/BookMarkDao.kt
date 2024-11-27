package com.example.e_book.data.tables

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_book.State
import kotlinx.coroutines.flow.Flow

@Dao
interface BookMarkDao {

    @Upsert
    suspend fun insertBookMark(bookMark: BookMark)

    @Delete
    suspend fun removeBookMark(bookMark: BookMark)

    @Query("DELETE FROM bookmark_table WHERE id = :id")
    suspend fun removeBookMarkById(id: Int)

    @Query("SELECT * FROM bookmark_table")
    fun getBookMarks(): Flow<List<BookMark>>
}