package com.delecrode.devhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.delecrode.devhub.ui.profile.ProfileScreen
import com.delecrode.devhub.ui.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val profileViewModel: ProfileViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = AppDestinations.Profile.route) {
        //Profile Flow
        composable(AppDestinations.Profile.route) {
            ProfileScreen(navController, profileViewModel)
        }
    }
}