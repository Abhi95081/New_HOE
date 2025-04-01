package com.example.new_hoe.Screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginPage(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }

    // Simple email and password validation
    val validateEmail = email.contains("@") && email.contains(".")
    val validatePassword = password.length >= 6

    // Animation state
    var enterAnimation by remember { mutableStateOf(false) }

    var context = LocalContext.current

    // Start animation
    LaunchedEffect(Unit) {
        enterAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animation for the login screen elements
        AnimatedVisibility(
            visible = enterAnimation,
            enter = fadeIn() + fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Text("Login Page", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Email Input
        AnimatedVisibility(visible = enterAnimation) {
            Column {
                Text("Email", style = MaterialTheme.typography.bodyLarge)
                BasicTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = validateEmail
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                        .padding(16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                if (!isEmailValid) {
                    Text("Invalid email", color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        AnimatedVisibility(visible = enterAnimation) {
            Column {
                Text("Password", style = MaterialTheme.typography.bodyLarge)
                BasicTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isPasswordValid = validatePassword
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                        .padding(16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                if (!isPasswordValid) {
                    Text("Password must be at least 6 characters", color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Button to navigate to the registration page
        AnimatedVisibility(visible = enterAnimation) {
            TextButton(
                onClick = {
                    navController.navigate("register")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Don't have an account? Register")
            }
        }

        // Handle login validation
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = enterAnimation) {
            Button(
                onClick = {
                    // Handle regular login
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the Bottom Navigation Bar
                    navController.navigate("bottom_nav") {
                        // Optional: Clear the back stack
                        popUpTo("login") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Login")
            }
        }
    }
}

