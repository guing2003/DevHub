package com.delecrode.devhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.delecrode.devhub.ui.RepoDetailScreen
import com.delecrode.devhub.ui.home.HomeScreen
import com.delecrode.devhub.ui.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val profileViewModel: HomeViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = AppDestinations.Home.route) {
        //Profile Flow
        composable(AppDestinations.Home.route) {
            HomeScreen(navController, profileViewModel)
        }

        composable(AppDestinations.RepoDetail.route) {
            RepoDetailScreen(navController)
        }
    }
}