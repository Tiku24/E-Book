package com.example.e_book.ui_layer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_book.State
import com.example.e_book.data.repo.Repo
import com.example.e_book.data.response.BookCategoryModels
import com.example.e_book.data.response.BookModels
import com.example.e_book.data.tables.BookMark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo: Repo) : ViewModel() {
    private val _getAllBookState = MutableStateFlow(GetAllBookState())
    val getAllBookState = _getAllBookState.asStateFlow()

    private val _getCategoryBookState = MutableStateFlow(GetCategoryBookState())
    val getCategoryBookState = _getCategoryBookState.asStateFlow()

    private val _getBooksByCategoryState = MutableStateFlow(GetAllBookState())
    val getBooksByCategoryState = _getBooksByCategoryState.asStateFlow()

    private val _getBookMarkState = MutableStateFlow(GetBookMarkState())
    val getBookMarkState = _getBookMarkState.asStateFlow()


    fun getBooksByCategory(category:String){
        viewModelScope.launch {
            repo.getBooksByCategory(category).collect{
                when(it){
                    is State.Success -> {
                        _getBooksByCategoryState.value = GetAllBookState(isLoading = false, data = it.data)
                        Log.d("CATe", it.data.toString())
                    }
                    is State.Error -> {
                        _getBooksByCategoryState.value = GetAllBookState(isLoading = false, error = it.exception)
                    }
                    is State.Loading -> {
                        _getBooksByCategoryState.value = GetAllBookState(isLoading = true)
                    }
                }
            }
        }
    }

    fun getCategoryBooks(){
        viewModelScope.launch {
            repo.getCategoryBooks().collect{
                when(it){
                    is State.Success -> {
                        _getCategoryBookState.value = GetCategoryBookState(isLoading = false, data = it.data)
                        Log.d("CATe", it.data.toString())
                    }
                    is State.Error -> {
                        _getCategoryBookState.value = GetCategoryBookState(isLoading = false, error = it.exception)
                    }
                    is State.Loading -> {
                        _getCategoryBookState.value = GetCategoryBookState(isLoading = true)
                    }
                }
            }
        }
    }


    fun getAllBooks(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllBooks().collect{
                when(it) {
                    is State.Success -> {
                        _getAllBookState.value = GetAllBookState(isLoading = false, data = it.data)
                    }
                    is State.Error -> {
                        _getAllBookState.value = GetAllBookState(isLoading = false, error = it.exception)
                    }
                    is State.Loading -> {
                        _getAllBookState.value = GetAllBookState(isLoading = true)
                    }
                }
            }
        }
    }
    fun addBookMark(bookMark: BookMark){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertBooMark(bookMark = bookMark)
        }
    }

    fun removeBookMark(bookMark: BookMark){
        viewModelScope.launch(Dispatchers.IO) {
            repo.removeBookMark(bookMark = bookMark)
        }
    }

    fun removeBookMarkById(id: Int){
        viewModelScope.launch {
            repo.removeBookMarkById(id)
        }
    }

    init {
        getBookMarkList()
    }

    private fun getBookMarkList() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBookMark().collectLatest {
                _getBookMarkState.value = GetBookMarkState(isLoading = false, data = it)
            }
    }
    }
}

data class GetAllBookState(
    val isLoading: Boolean=false,
    val data: List<BookModels> = emptyList(),
    val error: Throwable? = null
)

data class GetCategoryBookState(
    val isLoading: Boolean=false,
    val data: List<BookCategoryModels> = emptyList(),
    val error: Throwable? = null
)

data class GetBookMarkState(
    val isLoading: Boolean =false,
    val data: List<BookMark> = emptyList(),
    val error: String? = null
)

