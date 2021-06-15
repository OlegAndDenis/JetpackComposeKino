package com.example.kino.root

import AppNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.kino.extensions.currentRoute
import com.example.kino.navigation.BottomNavBar
import com.example.kino.navigation.blackList

@Composable
fun Root() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            if (!blackList.contains(navController.currentRoute())) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        AppNavigation(navController = navController, paddings = innerPadding)
    }
}