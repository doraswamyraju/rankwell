package com.reviewflow.ui.reviews

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerReviewScreen() {
    val context = LocalContext.current
    var selectedMode by remember { mutableStateOf("Smart") } // Custom, Smart, Ultra Smart
    
    // Smart Mode Q&A
    var favoriteThing by remember { mutableStateOf("") }
    var feedbackText by remember { mutableStateOf("") }
    
    // Ultra Smart Mode Configuration
    var rating by remember { mutableStateOf(5) }
    var serviceCategory by remember { mutableStateOf("Coffee") }
    var tone by remember { mutableStateOf("Friendly") }
    
    var aiDraft by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }
    var hasCopied by remember { mutableStateOf(false) }

    val googleReviewUrl = "https://search.google.com/local/writereview?placeid=ChIJu46S-uB-hYGRwT3QyP6Wj2Y"

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Share Your Feedback", color = Color.Gray, fontSize = 16.sp)
                Text("Blue Bottle Coffee", color = Color(0xFFFF8C00), fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            // Tab Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.opacity(0.1f), shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Smart", "Ultra Smart", "Custom").forEach { mode ->
                    Button(
                        onClick = { selectedMode = mode },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedMode == mode) Color(0xFFFF8C00) else Color.Transparent
                        ),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(mode, fontSize = 12.sp, color = Color.White)
                    }
                }
            }

            // Mode Forms
            if (selectedMode == "Smart") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.opacity(0.03f), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("What did you order or enjoy most?", color = Color.White, fontSize = 14.sp)
                    OutlinedTextField(
                        value = favoriteThing,
                        onValueChange = { favoriteThing = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Any quick comments about our service?", color = Color.White, fontSize = 14.sp)
                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            isGenerating = true
                            aiDraft = "I recently visited Blue Bottle Coffee and had a wonderful experience! I ordered a ${if (favoriteThing.isEmpty()) "Coffee" else favoriteThing}. $feedbackText The service was prompt and the staff were highly welcoming. Highly recommend!"
                            isGenerating = false
                            hasCopied = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate AI Review", color = Color.White)
                    }
                }
            } else if (selectedMode == "Ultra Smart") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.opacity(0.03f), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Rating:", color = Color.White)
                        Row {
                            (1..5).forEach { star ->
                                TextButton(
                                    onClick = { rating = star },
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.width(36.dp)
                                ) {
                                    Text(if (star <= rating) "★" else "☆", color = Color.Yellow, fontSize = 24.sp)
                                }
                            }
                        }
                    }

                    Text("Service/Product Type:", color = Color.White)
                    OutlinedTextField(
                        value = serviceCategory,
                        onValueChange = { serviceCategory = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Select Tone:", color = Color.White)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Friendly", "Professional").forEach { selectedTone ->
                            Button(
                                onClick = { tone = selectedTone },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (tone == selectedTone) Color(0xFFFF8C00) else Color.DarkGray
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(selectedTone, fontSize = 12.sp, color = Color.White)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            isGenerating = true
                            aiDraft = if (tone == "Friendly") {
                                "Absolutely loved the $serviceCategory here! The staff were so warm and welcoming. Easily a solid $rating-star experience. Can't wait to visit again!"
                            } else {
                                "The $serviceCategory provided was of exceptional quality. Highly professional staff and efficient service delivery. Rated $rating/5 for outstanding standards."
                            }
                            isGenerating = false
                            hasCopied = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate AI Review", color = Color.White)
                    }
                }
            } else {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleReviewUrl))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open Google Reviews Directly", color = Color.White)
                }
            }

            // AI Generated Output
            if (aiDraft.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0x11FF8C00))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Your Draft Review:", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(aiDraft, color = Color.White.opacity(0.8f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("review_draft", aiDraft)
                                    clipboard.setPrimaryClip(clip)
                                    hasCopied = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (hasCopied) Color.Green else Color.DarkGray
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(if (hasCopied) "Copied!" else "Copy Review", color = Color.White)
                            }

                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleReviewUrl))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4500)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Open Google", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Inline extension mapping for opacity values in Compose
private val Float.opacity: Float
    get() = this
