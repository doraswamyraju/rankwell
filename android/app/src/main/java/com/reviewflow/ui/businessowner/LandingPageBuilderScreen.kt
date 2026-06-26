package com.reviewflow.ui.businessowner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
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
fun LandingPageBuilderScreen(
    onBack: () -> Unit
) {
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

    var landingThemeColor by remember { mutableStateOf(Color(0xFFFF8C00)) }
    var description by remember { mutableStateOf("Tell us about your experience at Blue Bottle Coffee!") }
    var aiTone by remember { mutableStateOf("Professional") }
    var modeSmartSelected by remember { mutableStateOf(true) }
    var modeUltraSelected by remember { mutableStateOf(true) }
    var modeCustomSelected by remember { mutableStateOf(true) }
    var customQuestion by remember { mutableStateOf("How was the service today?") }
    var socialLinksJoined by remember { mutableStateOf("instagram.com/bluebottle") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Landing Page Builder", color = lightTextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = lightTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lightSurface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(lightBg)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Live Preview Card
            Text("LIVE MOBILE PREVIEW", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.5.dp, color = Color.LightGray.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = lightSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Logo Box
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(landingThemeColor, RoundedCornerShape(25.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("LOGO", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    Text("Blue Bottle Coffee", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(description, color = lightTextSecondary, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp))

                    Spacer(modifier = Modifier.height(4.dp))

                    // Simulated Options
                    if (modeSmartSelected) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(landingThemeColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .border(width = 1.dp, color = landingThemeColor, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("💡 Smart Q&A Mode", color = landingThemeColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }

                    if (modeUltraSelected) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⚡ Ultra Smart (Instant AI Review)", color = lightTextPrimary, fontSize = 13.sp)
                        }
                    }

                    if (modeCustomSelected) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("✍️ Custom Manual Review", color = lightTextPrimary, fontSize = 13.sp)
                        }
                    }
                }
            }

            // Description Editor
            Text("BUSINESS DESCRIPTION / CALL TO ACTION", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // Theme Color Choice
            Text("BRAND THEME COLOR", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                listOf(
                    Color(0xFFFF8C00),
                    Color(0xFF4A90E2),
                    Color(0xFF2ECC71),
                    Color(0xFFE91E63),
                    Color(0xFF9C27B0)
                ).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(color, RoundedCornerShape(22.dp))
                            .clickable { landingThemeColor = color }
                            .border(
                                width = 3.dp,
                                color = if (landingThemeColor == color) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(22.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (landingThemeColor == color) {
                            Icon(Icons.Default.Done, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            // Mode Selector Switches
            Text("ENABLED REVIEW FLOWS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = lightSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Smart Q&A Review Mode", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Switch(checked = modeSmartSelected, onCheckedChange = { modeSmartSelected = it })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ultra Smart Review Mode", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Switch(checked = modeUltraSelected, onCheckedChange = { modeUltraSelected = it })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Custom Manual Review Mode", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Switch(checked = modeCustomSelected, onCheckedChange = { modeCustomSelected = it })
                    }
                }
            }

            // AI settings & tone
            Text("AI REVIEW GENERATION TONE", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("Professional", "Casual", "Enthusiastic").forEach { tone ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (aiTone == tone) accentColor.copy(alpha = 0.15f) else Color.White,
                                RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.5.dp,
                                color = if (aiTone == tone) accentColor else Color.LightGray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { aiTone = tone }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tone, color = if (aiTone == tone) accentColor else lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // Custom Questions
            Text("CUSTOM SURVEY QUESTIONS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = customQuestion,
                onValueChange = { customQuestion = it },
                label = { Text("Q1 Custom Question") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // Social & Privacy Settings
            Text("SOCIAL PROFILE & PRIVACY LINKS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = socialLinksJoined,
                onValueChange = { socialLinksJoined = it },
                label = { Text("Instagram / Facebook URL") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // Submit Button
            Button(
                onClick = { onBack() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save Landing Page Layout", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
