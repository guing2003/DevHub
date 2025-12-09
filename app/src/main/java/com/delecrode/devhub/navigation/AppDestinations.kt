package com.delecrode.devhub.navigation

sealed class AppDestinations(val route: String) {

    object Home : AppDestinations("home")

    object RepoDetail : AppDestinations("repoDetail")
}