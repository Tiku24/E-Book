package com.example.e_book.ui_layer.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.e_book.navigation.Routes
import com.example.e_book.ui_layer.viewmodel.AppViewModel

@Composable
fun BookByCategoryUI(vm: AppViewModel,navController: NavController,category:String) {

    val bookState = vm.getBooksByCategoryState.collectAsState()
    val data = bookState.value.data ?: emptyList()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.getBooksByCategory(category)
    }

    when{
        bookState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        bookState.value.data != null -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(data){
                    Books(title = it.booksName, image = it.bookImage, author = it.bookAuthor, url = it.bookUrl, vm = vm, onClicked = {navController.navigate(
                        Routes.PdfViewer(it.bookUrl))})
                }
            }
    }
        bookState.value.error != null -> {
            Toast.makeText(context, bookState.value.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}