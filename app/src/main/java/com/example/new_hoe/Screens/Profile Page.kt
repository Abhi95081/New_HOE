package com.example.new_hoe.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfilePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Placeholder Profile Info
            Text("Name: John Doe", style = MaterialTheme.typography.bodyLarge)
            Text("Phone: +91 12345 67890", style = MaterialTheme.typography.bodyLarge)
            Text("Email: johndoe@example.com", style = MaterialTheme.typography.bodyLarge)
        }

        // Logout Button
        Button(
            onClick = {
                // Navigate back to login screen
                navController.navigate("login") {
                    popUpTo("bottom_nav") { inclusive = true } // Clear backstack
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Logout")
        }
    }
}
