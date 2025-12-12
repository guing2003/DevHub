package com.delecrode.devhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.delecrode.devhub.ui.forgot.ForgotPasswordScreen
import com.delecrode.devhub.ui.home.HomeScreen
import com.delecrode.devhub.ui.home.HomeViewModel
import com.delecrode.devhub.ui.login.LoginScreen
import com.delecrode.devhub.ui.register.RegisterScreen
import com.delecrode.devhub.ui.repo.RepoDetailScreen
import com.delecrode.devhub.ui.repo.RepoDetailViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val profileViewModel: HomeViewModel = koinViewModel()
    val repoViewModel: RepoDetailViewModel = koinViewModel()


    NavHost(navController = navController, startDestination = AppDestinations.Login.route) {
        //Home Flow
        composable(AppDestinations.Home.route) {
            HomeScreen(navController, profileViewModel)
        }

        //Repositorio Flow
        composable(
            AppDestinations.RepoDetail.route,
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repo") { type = NavType.StringType }
            )) {

            val owner = it.arguments?.getString("owner") ?: ""
            val repo = it.arguments?.getString("repo") ?: ""
            RepoDetailScreen(navController, repoViewModel, owner, repo)
        }

        //Register Flow
        composable(AppDestinations.Register.route) {
            RegisterScreen(navController)
        }

        //Login Flow
        composable(AppDestinations.Login.route) {
            LoginScreen(navController)
        }

        //Forgot Password Flow
        composable(AppDestinations.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }

    }
}