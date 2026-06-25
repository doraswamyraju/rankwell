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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlansPricingScreenLight() {
    var planName by remember { mutableStateOf("Growth Starter") }
    var monthlyPrice by remember { mutableStateOf("49") }
    var annualPrice by remember { mutableStateOf("490") }
    var maxBusinesses by remember { mutableStateOf("3") }
    var maxAiCredits by remember { mutableStateOf("1000") }
    var recommended by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }

    val lightTextPrimary = Color(0xFF212529)
    val lightSurface = Color.White
    val accentColor = Color(0xFFFF8C00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form Wrapper
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("ADD NEW PLAN", color = accentColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = planName,
                    onValueChange = { planName = it },
                    label = { Text("Plan Name") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = lightTextPrimary,
                        unfocusedTextColor = lightTextPrimary,
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.LightGray
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
                        label = { Text("Monthly Price ($)") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = lightTextPrimary,
                            unfocusedTextColor = lightTextPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = annualPrice,
                        onValueChange = { annualPrice = it },
                        label = { Text("Annual Price ($)") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = lightTextPrimary,
                            unfocusedTextColor = lightTextPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.LightGray
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
                        label = { Text("Max Businesses") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = lightTextPrimary,
                            unfocusedTextColor = lightTextPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = maxAiCredits,
                        onValueChange = { maxAiCredits = it },
                        label = { Text("Max AI Credits") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = lightTextPrimary,
                            unfocusedTextColor = lightTextPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Highlight Plan (Recommended)", color = lightTextPrimary, fontSize = 14.sp)
                    Switch(
                        checked = recommended,
                        onCheckedChange = { recommended = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = accentColor)
                    )
                }

                Button(
                    onClick = {
                        isSaving = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Plan", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
