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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuperAdminDashboardScreen(
    onNavigateToPlans: () -> Unit,
    onNavigateToUsers: () -> Unit,
    onNavigateToAuditLogs: () -> Unit
) {
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Super Admin Portal", color = Color.Gray, fontSize = 14.sp)
                    Text("RankWell System", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFF8C00), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("Live Mode", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main KPI Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PlatformKpiCard(
                    title = "PLATFORM MRR",
                    value = "$24,850",
                    growth = "+14.2%",
                    color = Color(0xFF2ECC71),
                    modifier = Modifier.weight(1f)
                )
                PlatformKpiCard(
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
                PlatformKpiCard(
                    title = "ACTIVE AGENCIES",
                    value = "84",
                    growth = "+12.1%",
                    color = Color(0xFF9B59B6),
                    modifier = Modifier.weight(1f)
                )
                PlatformKpiCard(
                    title = "AI CREDIT USAGE",
                    value = "458K",
                    growth = "+24.5%",
                    color = Color(0xFFFF8C00),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Admin Actions Directory
            Text("QUICK DIRECTORY", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)

            Button(
                onClick = onNavigateToPlans,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dynamic Plans & Pricing Module", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Configure →", color = Color(0xFFFF8C00))
                }
            }

            Button(
                onClick = onNavigateToUsers,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("User & Business Accounts Management", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Manage →", color = Color(0xFFFF8C00))
                }
            }

            Button(
                onClick = onNavigateToAuditLogs,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Security Audits & System Logs", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("View Logs →", color = Color(0xFFFF8C00))
                }
            }
        }
    }
}

@Composable
fun PlatformKpiCard(
    title: String,
    value: String,
    growth: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f))
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
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = title,
                color = Color.Gray,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private val Float.opacity: Float
    get() = this
