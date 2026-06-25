package com.reviewflow.ui.superadmin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuditLogsScreenLight() {
    val lightTextSecondary = Color(0xFF6C757D)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("AUDIT TIMELINE", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)

        LogItemLight(time = "14:24", user = "admin@rgv.com", action = "Modified pricing plan 'Growth Starter'", ip = "147.93.107.21")
        LogItemLight(time = "13:58", user = "john@cafe.com", action = "Onboarded business 'John Doe Cafe'", ip = "85.24.91.45")
        LogItemLight(time = "12:10", user = "admin@rgv.com", action = "Created coupon code 'WELCOME50'", ip = "147.93.107.21")
        LogItemLight(time = "10:30", user = "system", action = "Automated DB cache cleanup via Cron", ip = "localhost")
    }
}

@Composable
fun LogItemLight(
    time: String,
    user: String,
    action: String,
    ip: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                Text(ip, color = Color(0xFF6C757D), fontSize = 11.sp)
            }
            Text(action, color = Color(0xFF212529), fontSize = 14.sp)
            Text("Initiated by: $user", color = Color(0xFF6C757D), fontSize = 11.sp)
        }
    }
}
