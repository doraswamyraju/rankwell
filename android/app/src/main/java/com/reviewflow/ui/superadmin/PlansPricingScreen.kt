package com.reviewflow.ui.superadmin

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
fun PlansPricingScreen(
    onBack: () -> Unit
) {
    var planName by remember { mutableStateOf("Growth Starter") }
    var monthlyPrice by remember { mutableStateOf("49") }
    var annualPrice by remember { mutableStateOf("490") }
    var maxBusinesses by remember { mutableStateOf("3") }
    var maxAiCredits by remember { mutableStateOf("1000") }
    var recommended by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0A0D1A), Color(0xFF151932))
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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back", color = Color(0xFFFF8C00), fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Dynamic Pricing Engine", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Text(
                text = "Configure and manage subscription packages, billing structures, limits, and promo variables.",
                color = Color.Gray,
                fontSize = 13.sp
            )

            // Editor Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.03f), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("PLAN ATTRIBUTES", color = Color(0xFFFF8C00), fontSize = 12.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = planName,
                    onValueChange = { planName = it },
                    label = { Text("Plan Name", color = Color.Gray) },
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
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = monthlyPrice,
                        onValueChange = { monthlyPrice = it },
                        label = { Text("Monthly Price ($)", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = annualPrice,
                        onValueChange = { annualPrice = it },
                        label = { Text("Annual Price ($)", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = maxBusinesses,
                        onValueChange = { maxBusinesses = it },
                        label = { Text("Max Businesses", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = maxAiCredits,
                        onValueChange = { maxAiCredits = it },
                        label = { Text("Max AI Credits", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Display Recommended Badge", color = Color.White, fontSize = 14.sp)
                    Switch(
                        checked = recommended,
                        onCheckedChange = { recommended = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFFF8C00))
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        isSaving = true
                        // Action to save plan
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Save Plan Configuration", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

private val Float.opacity: Float
    get() = this
