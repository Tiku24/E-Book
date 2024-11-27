package com.example.e_book.ui_layer.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.e_book.data.tables.BookMark
import com.example.e_book.navigation.Routes
import com.example.e_book.ui_layer.viewmodel.AppViewModel

@Composable
fun AllBooksUI(vm:AppViewModel,navController: NavController) {
    val state = vm.getAllBookState.collectAsState()
    val data = state.value.data ?: emptyList()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        vm.getAllBooks()
    }

    when{
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.value.data != null -> {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(data){
                        Books(title = it.booksName, image = it.bookImage, author = it.bookAuthor, url = it.bookUrl, onClicked = {navController.navigate(Routes.PdfViewer(it.bookUrl))}, vm = vm)
                    }
                }
            }
        }
        state.value.error != null -> {
            Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Books(title:String,image:String,author:String,url:String,onClicked:()->Unit={},vm: AppViewModel) {
    var isBookMark by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClicked() }
    ) {
        Box(
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) { Icon(
            imageVector = if(isBookMark) Icons.Outlined.Bookmarks else Icons.Default.Bookmarks,
            contentDescription = null,
            modifier = Modifier.padding(8.dp).clickable {
                vm.addBookMark(
                    BookMark(
                        bookAuthor = author,
                        booksName = title,
                        bookUrl = url,
                        bookImage = image,
                        isBookmarked = true,
                        filePath = ""
                    )
                )
                isBookMark =!isBookMark
            }.size(24.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(model = image, contentDescription = title, alpha = 0.5f, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Text(text = title, color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)  }
    }}
}
