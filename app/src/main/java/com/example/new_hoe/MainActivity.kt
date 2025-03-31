package com.example.new_hoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.new_hoe.Screens.AddPage
import com.example.new_hoe.Screens.HomePage
import com.example.new_hoe.Screens.LoginPage
import com.example.new_hoe.Screens.ProfilePage
import com.example.new_hoe.Screens.RegistrationPage
import com.example.new_hoe.Screens.SearchPage
import com.example.new_hoe.Screens.SplashScreen
import com.example.new_hoe.ui.theme.New_HOETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            New_HOETheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginPage() }
        composable("register") { RegistrationPage() }
        composable("home") { HomePage() }
        composable("search") { SearchPage() }
        composable("profile") { ProfilePage() }
        composable("add"){ AddPage()}
    }
}
