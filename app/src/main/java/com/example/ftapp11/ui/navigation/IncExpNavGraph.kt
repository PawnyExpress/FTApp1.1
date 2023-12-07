package com.example.ftapp11.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ftapp11.data.DatabaseHandler
import com.example.ftapp11.ui.home.HomeDestination
import com.example.ftapp11.ui.home.HomeScreen
import com.example.ftapp11.ui.incexp.IncomeDetailsDestination
import com.example.ftapp11.ui.incexp.IncomeDetailsScreen
import com.example.ftapp11.ui.incexp.IncomeEditDestination
import com.example.ftapp11.ui.incexp.IncomeEditScreen
import com.example.ftapp11.ui.incexp.IncomeEntryDestination
import com.example.ftapp11.ui.incexp.IncomeEntryScreen

@Composable
fun IncExpNavHost(
    navController: NavHostController,
    databaseHandler : DatabaseHandler,
    modifier: Modifier = Modifier,

) {
    NavHost(
        navController = navController, startDestination = HomeDestination.route, modifier = modifier,
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToIncomeEntry = { navController.navigate(IncomeEntryDestination.route) },
                navigateToIncomeUpdate = {
                    navController.navigate("${IncomeDetailsDestination.route}/${it}")
                }, modifier , databaseHandler)
        }
        composable(route = IncomeEntryDestination.route) {
            IncomeEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp()}, true , databaseHandler)
        }
        composable(
            route = IncomeDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(IncomeDetailsDestination.incomeIdArg) {
                type = NavType.IntType
            })
        ) {
            IncomeDetailsScreen(
                navigateToEditIncome =
                {
                    navController.navigate("${IncomeEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() })
        }
        composable(
            route = IncomeEditDestination.routeWithArgs,
            arguments = listOf(navArgument(IncomeEditDestination.incomeIdArg) {
                type = NavType.IntType
            })
        ) {
            IncomeEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}