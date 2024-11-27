package com.example.e_book.ui_layer.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_book.data.tables.BookMark
import com.example.e_book.navigation.Routes
import com.example.e_book.ui_layer.viewmodel.AppViewModel

@Composable
fun BookMarkUI(vm:AppViewModel,navController: NavController) {
    val state = vm.getBookMarkState.collectAsState()
    val data = state.value.data ?: emptyList()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(data){
            BooksItem(title = it.booksName, image = it.bookImage, vm = vm,onClicked = {navController.navigate(
                Routes.PdfViewer(it.bookUrl))}, id = it.id!!)
        }
    }
}

@Composable
fun BooksItem(title:String,image:String,onClicked:()->Unit={},vm: AppViewModel,id:Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClicked() }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(2.dp), contentAlignment = Alignment.TopEnd
        ) {
            Icon(imageVector = Icons.Default.Bookmarks, contentDescription = null,
                modifier = Modifier.padding(8.dp)
                    .clickable {
                        vm.removeBookMarkById(id = id)
                    }.size(23.dp)
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AsyncImage(model = image, contentDescription = title, alpha = 0.5f,modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}