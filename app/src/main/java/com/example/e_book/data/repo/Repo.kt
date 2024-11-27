package com.example.e_book.data.repo

import android.util.Log
import com.example.e_book.State
import com.example.e_book.data.database.BookMarkDatabase
import com.example.e_book.data.response.BookCategoryModels
import com.example.e_book.data.response.BookModels
import com.example.e_book.data.tables.BookMark
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.flow

class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase,private val database: BookMarkDatabase) {

    suspend fun getAllBooks(): Flow<State<List<BookModels>>> = callbackFlow {
        trySend(State.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookModels>()!!
                }
                trySend(State.Success(items))
                Log.d("CATe", items.toString())
            }

            override fun onCancelled(error: DatabaseError) {
               trySend(State.Error(error.toException()))
            }

        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }
    suspend fun getCategoryBooks(): Flow<State<List<BookCategoryModels>>> = callbackFlow {
        trySend(State.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookCategoryModels> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookCategoryModels>()!!
                }
                trySend(State.Success(items))
                Log.d("CATe", items.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(State.Error(error.toException()))
            }

        }
        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }
    suspend fun getBooksByCategory(category:String): Flow<State<List<BookModels>>> = callbackFlow {
        trySend(State.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = snapshot.children.filter {
                    it.getValue<BookModels>()!!.category == category
                }.map {
                    it.getValue<BookModels>()!!
                }
                trySend(State.Success(items))
            }
            override fun onCancelled(error: DatabaseError) {
                trySend(State.Error(error.toException()))
            }
        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    suspend fun insertBooMark(bookMark: BookMark){
        database.getBookMarkDao().insertBookMark(bookMark)
    }

    suspend fun removeBookMark(bookMark: BookMark){
        database.getBookMarkDao().removeBookMark(bookMark)
    }

    suspend fun removeBookMarkById(id: Int){
        database.getBookMarkDao().removeBookMarkById(id)
    }

    fun getBookMark(): Flow<List<BookMark>>{
        return database.getBookMarkDao().getBookMarks()
    }
}