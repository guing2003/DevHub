package com.delecrode.devhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.delecrode.devhub.ui.profile.ProfileScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppDestinations.Profile.route) {

        //Profile Flow
        composable(AppDestinations.Profile.route) {
            ProfileScreen(navController)
        }
    }
}