package com.rondi.indonesianspecialfood.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("home/{foodsId}") {
        fun createRoute(foodsId: Int) = "home/$foodsId"
    }
}