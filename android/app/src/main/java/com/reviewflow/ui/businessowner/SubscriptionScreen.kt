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

@Composable
fun SubscriptionScreen(
    onNavigateToShop: () -> Unit
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
        Text("Billing & Subscriptions", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)

        // Active Subscription Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("ACTIVE PLAN", color = accentColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFD4EDDA), shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("PAID", color = Color(0xFF155724), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text("Growth Premium Plan", color = lightTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Black)
                Text("Renewal Price: $49/month (Next billing date: July 25, 2026)", color = lightTextSecondary, fontSize = 12.sp)

                Divider(color = Color.LightGray.copy(alpha = 0.5f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("AI Credits: 458 / 1000", color = lightTextSecondary, fontSize = 12.sp)
                        Text("WhatsApp: 152 / 5000", color = lightTextSecondary, fontSize = 12.sp)
                    }
                    Button(
                        onClick = onNavigateToShop,
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Upgrade", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

