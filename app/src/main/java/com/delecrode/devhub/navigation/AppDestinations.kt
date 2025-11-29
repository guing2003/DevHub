package com.delecrode.devhub.navigation

sealed class AppDestinations(val route: String) {

    object Profile : AppDestinations("profile")
}