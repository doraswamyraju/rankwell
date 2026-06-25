package com.reviewflow.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onAuthSuccess: (role: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Premium Light Mode Palette
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00) // RankWell Orange
    val gradientColors = listOf(Color(0xFFFF8C00), Color(0xFFFF4500))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Typography logo
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "RankWell",
                    color = lightTextPrimary,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "A product of RGV",
                    color = lightTextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // White Elevation Container Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = lightSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (isSignUp) "Create Account" else "Welcome Back",
                        color = lightTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                    )

                    if (isSignUp) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = lightTextPrimary,
                                unfocusedTextColor = lightTextPrimary,
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = lightTextPrimary,
                            unfocusedTextColor = lightTextPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = lightTextPrimary,
                            unfocusedTextColor = lightTextPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(errorMessage, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Gradient Submit Button
                    Button(
                        onClick = {
                            isLoading = true
                            // Trigger callback matching credential seed roles
                            if (email.trim() == "doraswamyraju.ca@gmail.com") {
                                onAuthSuccess("admin")
                            } else {
                                onAuthSuccess("owner")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(
                                Brush.horizontalGradient(colors = gradientColors),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = if (isSignUp) "Sign Up" else "Log In",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    TextButton(onClick = { isSignUp = !isSignUp }) {
                        Text(
                            text = if (isSignUp) "Already have an account? Log In" else "Don't have an account? Sign Up",
                            color = accentColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
