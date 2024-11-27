package com.example.e_book

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception:Throwable) : State<Nothing>()
}