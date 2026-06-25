package com.reviewflow.ui.superadmin

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

@Composable
fun SuperAdminDashboardScreenLight(
    onNavigateToPlans: () -> Unit,
    onNavigateToUsers: () -> Unit,
    onNavigateToAuditLogs: () -> Unit
) {
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val lightSurface = Color.White
    val accentColor = Color(0xFFFF8C00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Header
        Column {
            Text("Overview Stats", color = lightTextSecondary, fontSize = 14.sp)
            Text("Rajugari Ventures Portal", color = lightTextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Metrics Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KpiCardLight(
                title = "PLATFORM MRR",
                value = "$24,850",
                growth = "+14.2%",
                color = Color(0xFF2ECC71),
                modifier = Modifier.weight(1f)
            )
            KpiCardLight(
                title = "TOTAL BUSINESSES",
                value = "1,240",
                growth = "+8.5%",
                color = Color(0xFF4A90E2),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KpiCardLight(
                title = "ACTIVE AGENCIES",
                value = "84",
                growth = "+12.1%",
                color = Color(0xFF9B59B6),
                modifier = Modifier.weight(1f)
            )
            KpiCardLight(
                title = "AI CREDIT USAGE",
                value = "458K",
                growth = "+24.5%",
                color = accentColor,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("QUICK CONFIGURATIONS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)

        // Action items
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                TextButton(
                    onClick = onNavigateToPlans,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Dynamic Pricing Plans", color = lightTextPrimary, fontWeight = FontWeight.Bold)
                        Text("Configure →", color = accentColor)
                    }
                }
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
                TextButton(
                    onClick = onNavigateToUsers,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Manage User Directory", color = lightTextPrimary, fontWeight = FontWeight.Bold)
                        Text("Manage →", color = accentColor)
                    }
                }
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
                TextButton(
                    onClick = onNavigateToAuditLogs,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("System Security & Audit Logs", color = lightTextPrimary, fontWeight = FontWeight.Bold)
                        Text("Logs →", color = accentColor)
                    }
                }
            }
        }
    }
}

@Composable
fun KpiCardLight(
    title: String,
    value: String,
    growth: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                )
                Text(text = growth, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Text(
                text = value,
                color = Color(0xFF212529),
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = title,
                color = Color(0xFF6C757D),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
