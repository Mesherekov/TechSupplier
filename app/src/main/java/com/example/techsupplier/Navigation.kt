package com.example.techsupplier

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController,
               details: List<Detail>) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            DetailsList(details)
        }
        composable("profile") {
            Account(details)
        }
    }
}