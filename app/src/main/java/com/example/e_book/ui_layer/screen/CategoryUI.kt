package com.example.e_book.ui_layer.screen

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_book.navigation.Routes
import com.example.e_book.ui_layer.viewmodel.AppViewModel

@Composable
fun CategoryUI(vm:AppViewModel,navController: NavController) {
    val state = vm.getCategoryBookState.collectAsState()
    val data = state.value.data ?: emptyList()
    val context = LocalContext.current
    val rows = data.chunked(3)
    val colors = listOf(
        Color(0xFFFCC97A),
        Color(0xFFDDE0FA)
    )

    LaunchedEffect(Unit) {
        vm.getCategoryBooks()
    }

    when{
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.value.data != null -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 10.dp)) {
                itemsIndexed(rows) { rowIndex,rowItems ->
                    Row(
                        modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowItems.forEachIndexed { itemIndex, item ->
                            val backgroundColor = colors[(rowIndex * 1 + itemIndex) % colors.size]
                            Box(
                                modifier = Modifier
                                    .background(backgroundColor, shape = RoundedCornerShape(50))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable { navController.navigate(Routes.BookByCategory(category = item.name)) }
                                    .weight(1f)
                                    .size(25.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    fontSize = 14.5.sp,
                                    text = item.name,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
        state.value.error != null -> {
            Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
