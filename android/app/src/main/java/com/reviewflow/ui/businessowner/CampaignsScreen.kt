package com.reviewflow.ui.businessowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CampaignsScreen(
    onNavigateToDesigner: () -> Unit
) {
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

    var selectedCampaignTab by remember { mutableStateOf(0) } // 0: QR Channels, 1: Messaging Channels

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Campaigns Ledger", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = onNavigateToDesigner,
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("+ New QR Campaign", color = Color.White, fontSize = 12.sp)
            }
        }

        TabRow(
            selectedTabIndex = selectedCampaignTab,
            containerColor = Color.Transparent,
            contentColor = accentColor
        ) {
            Tab(selected = selectedCampaignTab == 0, onClick = { selectedCampaignTab = 0 }) {
                Text("QR / Physical Channels", modifier = Modifier.padding(12.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Tab(selected = selectedCampaignTab == 1, onClick = { selectedCampaignTab = 1 }) {
                Text("SMS / WhatsApp Channels", modifier = Modifier.padding(12.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (selectedCampaignTab == 0) {
            CampaignItemLight(name = "Table Tent QR", type = "Table QR", scans = 84, conversions = 52)
            CampaignItemLight(name = "Receipt Print QR", type = "Receipt QR", scans = 42, conversions = 28)
            CampaignItemLight(name = "Window Sticker QR", type = "Poster QR", scans = 22, conversions = 12)
            CampaignItemLight(name = "NFC Table Card", type = "NFC Card", scans = 64, conversions = 48)
            CampaignItemLight(name = "Invoice Print QR", type = "Invoice QR", scans = 15, conversions = 9)
        } else {
            CampaignItemLight(name = "Weekend SMS Blast", type = "SMS Campaign", scans = 180, conversions = 32)
            CampaignItemLight(name = "Post-Purchase WhatsApp", type = "WhatsApp Campaign", scans = 310, conversions = 76)
            CampaignItemLight(name = "Email Review Invites", type = "Email Campaign", scans = 95, conversions = 14)
        }
    }
}

@Composable
fun CampaignItemLight(
    name: String,
    type: String,
    scans: Int,
    conversions: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(name, color = Color(0xFF212529), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Type: $type", color = Color(0xFF6C757D), fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Scans/Clicks: $scans", color = Color.Gray, fontSize = 11.sp)
                    Text("Conversions: $conversions", color = Color(0xFFFF8C00), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
            OutlinedButton(
                onClick = { /* Action to preview or download */ },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Export", fontSize = 11.sp)
            }
        }
    }
}

