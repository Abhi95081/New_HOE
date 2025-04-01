package com.example.new_hoe.NavigationBar


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.new_hoe.Routes.Routes
import com.example.new_hoe.Screens.AddPage
import com.example.new_hoe.Screens.HomePage
import com.example.new_hoe.Screens.LoginPage
import com.example.new_hoe.Screens.ProfilePage
import com.example.new_hoe.Screens.RegistrationPage
import com.example.new_hoe.Screens.SearchPage
import com.example.new_hoe.Screens.SplashScreen


@Composable
fun Navgraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {

        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Routes.HomePage.route) {
            HomePage()
        }
        composable(Routes.SearchPage.route) {
            SearchPage()
        }
        composable(Routes.ProfilePage.route) {
            ProfilePage()
        }
        composable(Routes.AddPage.route) {
            AddPage()
        }
        composable(Routes.BottomNav.route) {
            BottomNav(navController)
        }
        composable(Routes.LoginPage.route) {
            LoginPage(navController)
        }
        composable(Routes.RegistrationPage.route) {
            RegistrationPage()
        }

    }

}