package com.rondi.indonesianspecialfood

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rondi.indonesianspecialfood.ui.navigation.NavigationItem
import com.rondi.indonesianspecialfood.ui.navigation.Screen
import com.rondi.indonesianspecialfood.ui.screen.about.AboutScreen
import com.rondi.indonesianspecialfood.ui.screen.detail.DetailScreen
import com.rondi.indonesianspecialfood.ui.screen.favorite.FavoriteScreen
import com.rondi.indonesianspecialfood.ui.screen.home.HomeScreen
import com.rondi.indonesianspecialfood.ui.theme.IndonesianSpecialFoodTheme

@Composable
fun IndonesianFoodsListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.About.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { tourismId ->
                        navController.navigate(Screen.Detail.createRoute(tourismId))
                    },
                    moveToAboutPage = {
                        navController.navigate(Screen.About.route)
                    },
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    moveToAboutPage = {
                        navController.navigate(Screen.About.route)
                    },
                    navigateToDetail = { tourismId ->
                        navController.navigate(Screen.Detail.createRoute(tourismId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("foodsId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("foodsId") ?: -1
                DetailScreen(
                    foodsId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        backgroundColor = Color.LightGray,
        modifier = modifier
            .shadow(48.dp)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Rounded.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Rounded.FavoriteBorder,
                screen = Screen.Favorite
            ),
        )
        BottomNavigation(
            backgroundColor = Color.White,
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    selectedContentColor = MaterialTheme.colors.primaryVariant,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndonesianFoodsListAppPreview() {
    IndonesianSpecialFoodTheme {
        IndonesianFoodsListApp()
    }
}