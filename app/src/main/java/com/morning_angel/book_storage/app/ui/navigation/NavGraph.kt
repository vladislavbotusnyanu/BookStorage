package com.morning_angel.book_storage.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.morning_angel.book_storage.data.ui.navigation.Screens

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    innerPadding : PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = Screens.BookList.route
    ){
        composable(Screens.BookList.route){

        }
        composable(Screens.ServerSettings.route){

        }
    }
}