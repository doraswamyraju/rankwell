package com.reviewflow.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var googleMapsUrl by remember { mutableStateOf("") }
    var placeId by remember { mutableStateOf("") }
    var generatedReviewUrl by remember { mutableStateOf("") }
    var isResolving by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var isPreviewActive by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F0F1A), Color(0xFF1E102F))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Add Your Business",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Paste your Google Maps link to resolve your Place ID and generate direct review links.",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = businessName,
                onValueChange = { businessName = it },
                label = { Text("Business Name", color = Color.Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFFF8C00),
                    unfocusedBorderColor = Color.DarkGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address (Optional)", color = Color.Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFFF8C00),
                    unfocusedBorderColor = Color.DarkGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = googleMapsUrl,
                    onValueChange = { googleMapsUrl = it },
                    label = { Text("Google Maps Link", color = Color.Gray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFF8C00),
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        isResolving = true
                        // Mock resolving
                        placeId = "ChIJu46S-uB-hYGRwT3QyP6Wj2Y"
                        generatedReviewUrl = "https://search.google.com/local/writereview?placeid=$placeId"
                        isResolving = false
                        isPreviewActive = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isResolving) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Verify")
                    }
                }
            }

            if (isPreviewActive) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0x11FF8C00))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Resolved Place ID:", color = Color.Gray, fontSize = 12.sp)
                        Text(placeId, color = Color.Yellow, fontWeight = FontWeight.Bold)

                        Text("Google Review URL:", color = Color.Gray, fontSize = 12.sp)
                        Text(generatedReviewUrl, color = Color(0xFFFF8C00), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isSaving = true
                    onComplete()
                },
                enabled = businessName.isNotEmpty() && googleMapsUrl.isNotEmpty() && !isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4500))
            ) {
                Text("Onboard Business", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
