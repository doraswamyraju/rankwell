package com.reviewflow.ui.businessowner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reviewflow.ui.superadmin.KpiCardLight
import com.reviewflow.ui.superadmin.PlatformKpiCard

@Composable
fun BusinessDashboardScreen(
    onNavigateToCampaigns: () -> Unit,
    onNavigateToContacts: () -> Unit
) {
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)
    val lightSurface = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Header
        Column {
            Text("Business Dashboard", color = lightTextSecondary, fontSize = 14.sp)
            Text("Blue Bottle Coffee", color = lightTextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // KPI Metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KpiCardLight(
                title = "QR SCANS",
                value = "148",
                growth = "+18.2%",
                color = Color(0xFF4A90E2),
                modifier = Modifier.weight(1f)
            )
            KpiCardLight(
                title = "CONVERSIONS",
                value = "92",
                growth = "+10.5%",
                color = Color(0xFF2ECC71),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KpiCardLight(
                title = "CONVERSION RATE",
                value = "62.1%",
                growth = "+5.4%",
                color = accentColor,
                modifier = Modifier.weight(1f)
            )
            KpiCardLight(
                title = "ACTIVE CAMPAIGNS",
                value = "4",
                growth = "Stable",
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Conversion Funnel Section
        Text("CONVERSION FUNNEL", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FunnelRowLight(label = "QR Code Scanned", count = "148", percent = 100)
                FunnelRowLight(label = "Selected Smart Mode", count = "94", percent = 63)
                FunnelRowLight(label = "Posted Google Review", count = "92", percent = 62)
            }
        }
    }
}

@Composable
fun FunnelRowLight(label: String, count: String, percent: Int) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color(0xFF212529), fontSize = 13.sp)
            Text("$count ($percent%)", color = Color(0xFF6C757D), fontSize = 13.sp)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(Color.LightGray.copy(alpha = 0.3f), shape = RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percent / 100f)
                    .fillMaxHeight()
                    .background(Color(0xFFFF8C00), shape = RoundedCornerShape(3.dp))
            )
        }
    }
}
