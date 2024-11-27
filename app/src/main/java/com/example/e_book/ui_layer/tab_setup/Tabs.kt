package com.example.e_book.ui_layer.tab_setup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavHostController
import com.example.e_book.ui_layer.screen.AllBooksUI
import com.example.e_book.ui_layer.screen.BookMarkUI
import com.example.e_book.ui_layer.screen.CategoryUI
import com.example.e_book.ui_layer.viewmodel.AppViewModel
import kotlinx.coroutines.launch


@Composable
fun Tabs(navController: NavHostController,vm: AppViewModel) {
    val tabList = listOf(
        TabItem ("Category", Icons.Rounded.Category),
        TabItem("All Books" ,Icons.Rounded.Book),
        TabItem("BookMarks",Icons.Rounded.Bookmarks)
    )
    val pagerState = rememberPagerState(pageCount = {tabList.size})

    val scope = rememberCoroutineScope()
    Column (modifier = Modifier.fillMaxSize()){
        TabRow(selectedTabIndex = pagerState.currentPage, modifier = Modifier.fillMaxWidth()){
            tabList.fastForEachIndexed { i, tab ->
                Tab(selected = pagerState.currentPage == i,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(i)
                        }
                    },
                    text = {
                        Text(tab.name)
                    },
                    icon = {
                        Icon(imageVector = tab.icon, contentDescription = null)
                    }
                )

            }
        }
        HorizontalPager(state = pagerState) {
            when(it){
                0 -> CategoryUI(navController = navController, vm = vm)
                1 -> AllBooksUI(navController = navController, vm = vm)
                2 -> BookMarkUI(navController=navController, vm = vm)
            }
        }
    }
}


data class TabItem(
    val name:String,
    val icon:ImageVector
)