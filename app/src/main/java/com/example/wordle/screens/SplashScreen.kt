package com.example.wordle.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordle.R
import kotlinx.coroutines.delay
/**
 * Composable function that displays the splash screen.
 * The splash screen shows the logo and navigates to the main page after a delay.
 * @param navController The NavController used for navigation.
 */
@Composable
fun SplashScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wordlelogo),
                contentDescription = null,
                modifier = Modifier.size(320.dp)
            )
        }
    }
// Launches a coroutine when the composable enters the composition
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("mainPage") {
            popUpTo("splashScreen") { inclusive = true }
        }
    }
}