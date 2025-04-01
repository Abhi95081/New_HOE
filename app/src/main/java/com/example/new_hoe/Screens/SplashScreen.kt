package com.example.new_hoe.Screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Wait for 2 seconds
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true } // Remove splash from back stack
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Blue, Color.Cyan))),
          contentAlignment = Alignment.Center
    ) {
        Text(text = "Welcome to Hostel Online Exchange"
            , modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
    }
}
