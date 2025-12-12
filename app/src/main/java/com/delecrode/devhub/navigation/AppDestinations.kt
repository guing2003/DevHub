package com.delecrode.devhub.navigation

sealed class AppDestinations(val route: String) {

    object Home : AppDestinations("home/{uid}"){
        fun createRoute(uid: String) = "home/$uid"
    }

    object RepoDetail : AppDestinations("repoDetail/{owner}/{repo}"){
        fun createRoute(owner: String, repo: String) = "repoDetail/$owner/$repo"
    }
    object Register: AppDestinations("register")
    object Login: AppDestinations("login")
    object ForgotPassword: AppDestinations("forgotPassword")
}