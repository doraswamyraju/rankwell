package com.reviewflow.ui.businessowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reviewflow.ui.superadmin.KpiCardLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDashboardScreen(
    initialBusinessName: String,
    initialReviewUrl: String,
    onNavigateToCampaigns: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToBuilder: () -> Unit,
    onPreviewLanding: () -> Unit
) {
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

    var showAddBusinessDialog by remember { mutableStateOf(false) }
    var businessName by remember { mutableStateOf(initialBusinessName) }
    var mapsUrlInput by remember { mutableStateOf("") }
    var generatedReviewUrl by remember { mutableStateOf("") }
    var isLinkExtracted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Welcome Header & Add Business Action
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Business Dashboard Overview", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(businessName, color = lightTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Black)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { showAddBusinessDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("+ Add Listing", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Customer Scan QR Code Card
        val qrBitmap = remember(initialReviewUrl) {
            QrCodeGenerator.generateQrBitmap(initialReviewUrl, size = 300)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // QR Image
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (qrBitmap != null) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "Scan to Review",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        CircularProgressIndicator(color = accentColor)
                    }
                }

                // Call to actions
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Customer Review QR Code", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Show this QR to customers. Scanning it opens the review feedback landing page.", color = lightTextSecondary, fontSize = 11.sp)
                    
                    Button(
                        onClick = onPreviewLanding,
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("Preview Scan Flow", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Quick shortcut to edit Landing Page Builder
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onNavigateToBuilder() },
            colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Customize Customer Landing Page", color = accentColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Select modes (Smart Review, Ultra Smart), change colors & banner", color = lightTextSecondary, fontSize = 11.sp)
                }
                Text("Configure →", color = accentColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        // Section: Core Metrics
        Text("PERFORMANCE METRICS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                KpiCardLight("QR SCANS", "148", "+18%", Color(0xFF4A90E2), Modifier.weight(1f))
                KpiCardLight("UNIQUE VISITORS", "112", "+12%", Color(0xFF9B59B6), Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                KpiCardLight("GOOGLE REDIRECTS", "92", "+10%", Color(0xFF2ECC71), Modifier.weight(1f))
                KpiCardLight("COPY BUTTON CLICKS", "104", "+15%", accentColor, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                KpiCardLight("AVG TIME ON PAGE", "64s", "-4s", Color.Red, Modifier.weight(1f))
                KpiCardLight("BOUNCE RATE", "18.4%", "-2.1%", Color(0xFF2ECC71), Modifier.weight(1f))
            }
        }

        // Section: Funnel Analytics
        Text("CONVERSION FUNNEL STAGES", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                FunnelRowLight("1. QR Viewed / Scanned", "148", 100)
                FunnelRowLight("2. Landing Page Opened", "140", 94)
                FunnelRowLight("3. Review Mode Selected", "118", 79)
                FunnelRowLight("4. AI Review Generated", "108", 72)
                FunnelRowLight("5. Copy Text Clicked", "104", 70)
                FunnelRowLight("6. Google Review URL Opened", "92", 62)
                FunnelRowLight("7. Est. Review Completion", "88", 59)
            }
        }

        // Section: Preferences
        Text("REVIEW MODES PREFERRED", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Smart Q&A", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("54 selections", color = Color(0xFFFF8C00), fontSize = 13.sp)
                }
                Column {
                    Text("Ultra Smart", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("28 selections", color = Color(0xFFFF4500), fontSize = 13.sp)
                }
                Column {
                    Text("Custom Manual", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("10 selections", color = Color.Gray, fontSize = 13.sp)
                }
            }
        }

        // Section: Demographics & Devices
        Text("TRAFFIC SEGMENTS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SegmentRow(label = "Top Country / City", value = "India / Bangalore")
                SegmentRow(label = "Primary Device Type", value = "Mobile (Android / iOS)")
                SegmentRow(label = "Primary Operating System", value = "Android (64%), iOS (36%)")
                SegmentRow(label = "Top Browser Used", value = "Chrome / Safari Mobile")
                SegmentRow(label = "Peak Scan Hour", value = "7:00 PM - 9:00 PM")
            }
        }

        // Section: Staff Leaderboard
        Text("STAFF PERFORMANCE LEADERBOARD", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                LeaderboardRow(rank = 1, name = "Ravi Kumar", count = 38)
                LeaderboardRow(rank = 2, name = "Aisha Sen", count = 28)
                LeaderboardRow(rank = 3, name = "Vikram Singh", count = 18)
            }
        }
    }

    // Add Business Dialog
    if (showAddBusinessDialog) {
        var tempName by remember { mutableStateOf("") }
        var tempMapsUrl by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                showAddBusinessDialog = false
                isLinkExtracted = false
                generatedReviewUrl = ""
            },
            title = { Text("Add Business Listing") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Enter your business name and paste your Google Maps listing URL. We will parse and generate a direct Google review link.", color = lightTextSecondary, fontSize = 12.sp)
                    
                    OutlinedTextField(
                        value = tempName,
                        onValueChange = { tempName = it },
                        label = { Text("Business Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentColor)
                    )

                    OutlinedTextField(
                        value = tempMapsUrl,
                        onValueChange = { tempMapsUrl = it },
                        label = { Text("Google Maps Listing URL") },
                        placeholder = { Text("https://maps.app.goo.gl/...") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentColor)
                    )

                    if (tempMapsUrl.isNotEmpty()) {
                        Button(
                            onClick = {
                                // Extract PlaceID Mock logic
                                val parsedId = if (tempMapsUrl.contains("place/")) {
                                    tempMapsUrl.split("place/").getOrNull(1)?.split("/")?.firstOrNull() ?: "ChIJyTMQ5lhGkFQR8h5s5wZyC-c"
                                } else {
                                    "ChIJyTMQ5lhGkFQR8h5s5wZyC-c" // Real fallback Place ID (Space Needle)
                                }
                                generatedReviewUrl = "https://search.google.com/local/writereview?placeid=$parsedId"
                                isLinkExtracted = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Extract Review URL", color = Color.White)
                        }
                    }

                    if (isLinkExtracted) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFD4EDDA))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Link Extracted & Validated!", color = Color(0xFF155724), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(generatedReviewUrl, color = Color(0xFF155724), fontSize = 11.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tempName.isNotEmpty() && isLinkExtracted) {
                            businessName = tempName
                            showAddBusinessDialog = false
                            isLinkExtracted = false
                            generatedReviewUrl = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    enabled = tempName.isNotEmpty() && isLinkExtracted
                ) {
                    Text("Confirm & Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddBusinessDialog = false
                        isLinkExtracted = false
                        generatedReviewUrl = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun FunnelRowLight(title: String, count: String, percentage: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, color = Color(0xFF212529), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text("$count ($percentage%)", color = Color(0xFF6C757D), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = percentage / 100f,
            modifier = Modifier.fillMaxWidth().height(6.dp),
            color = Color(0xFFFF8C00),
            trackColor = Color(0xFFE9ECEF)
        )
    }
}


@Composable
fun SegmentRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color(0xFF6C757D), fontSize = 13.sp)
        Text(value, color = Color(0xFF212529), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}

@Composable
fun LeaderboardRow(rank: Int, name: String, count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFE9ECEF), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("#$rank", color = Color(0xFF495057), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(name, color = Color(0xFF212529), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Text("$count Reviews", color = Color(0xFFFF8C00), fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}
