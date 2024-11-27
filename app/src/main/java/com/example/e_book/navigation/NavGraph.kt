package com.example.e_book.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.data.tables.BookMark
import com.example.e_book.ui_layer.screen.AllBooksUI
import com.example.e_book.ui_layer.screen.BookByCategoryUI
import com.example.e_book.ui_layer.screen.BookMarkUI
import com.example.e_book.ui_layer.screen.PdfViewUI
import com.example.e_book.ui_layer.tab_setup.Tabs
import com.example.e_book.ui_layer.viewmodel.AppViewModel

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val vm: AppViewModel = hiltViewModel()
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.Home) {
            composable<Routes.Home> {
                Tabs(navController, vm = vm)
            }
            composable<Routes.PdfViewer> {
                val data = it.toRoute<Routes.PdfViewer>()
                PdfViewUI(pdfUrl = data.pdfUrl)
            }
            composable<Routes.BookByCategory> {
                val data = it.toRoute<Routes.BookByCategory>()
                BookByCategoryUI(navController = navController, category = data.category, vm = vm)
            }
            composable<Routes.BookMark> {
                BookMarkUI(vm = vm, navController)
            }
        }
    }
}