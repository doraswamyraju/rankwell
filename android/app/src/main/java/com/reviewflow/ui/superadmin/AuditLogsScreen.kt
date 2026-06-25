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
fun AuditLogsScreen(
    onBack: () -> Unit
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back", color = Color(0xFFFF8C00), fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Audit Logs & Logs", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Logs Timeline
            Text("PLATFORM SECURITY TIMELINE", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)

            LogItem(time = "14:24", user = "admin@rgv.com", action = "Modified pricing plan 'Growth Starter'", ip = "147.93.107.21")
            LogItem(time = "13:58", user = "john@cafe.com", action = "Onboarded business 'John Doe Cafe'", ip = "85.24.91.45")
            LogItem(time = "12:10", user = "admin@rgv.com", action = "Created coupon code 'WELCOME50'", ip = "147.93.107.21")
            LogItem(time = "10:30", user = "system", action = "Automated DB cache cleanup via Cron", ip = "localhost")
        }
    }
}

@Composable
fun LogItem(
    time: String,
    user: String,
    action: String,
    ip: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(time, color = Color(0xFFFF8C00), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(ip, color = Color.Gray, fontSize = 11.sp)
            }
            Text(action, color = Color.White, fontSize = 14.sp)
            Text("Initiated by: $user", color = Color.Gray, fontSize = 11.sp)
        }
    }
}

private val Float.opacity: Float
    get() = this
