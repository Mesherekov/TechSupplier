package com.example.techsupplier

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
    details: List<Detail>,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            DetailsList(details, innerPadding)
        }
        composable("profile") {
            Account(details, innerPadding)
        }
    }
}