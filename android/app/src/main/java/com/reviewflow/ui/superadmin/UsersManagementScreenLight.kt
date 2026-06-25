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
fun UsersManagementScreenLight() {
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val lightSurface = Color.White
    val accentColor = Color(0xFFFF8C00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Users / Filter by Role", color = lightTextSecondary)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("ACTIVE PLATFORM USERS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)

        UserListItemLight(name = "Rajugari Ventures", email = "admin@rgv.com", role = "Agency", status = "Active")
        UserListItemLight(name = "John Doe Cafe", email = "john@cafe.com", role = "Business Owner", status = "Active")
        UserListItemLight(name = "Staff member A", email = "staff1@cafe.com", role = "Staff", status = "Active")
        UserListItemLight(name = "Blocked user", email = "spam@domain.com", role = "Business Owner", status = "Suspended")
    }
}

@Composable
fun UserListItemLight(
    name: String,
    email: String,
    role: String,
    status: String
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
            Column {
                Text(name, color = Color(0xFF212529), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(email, color = Color(0xFF6C757D), fontSize = 12.sp)
                Text("Role: $role", color = Color(0xFFFF8C00), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }

            Box(
                modifier = Modifier
                    .background(
                        if (status == "Active") Color(0x222ECC71) else Color(0x22E74C3C),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = status,
                    color = if (status == "Active") Color(0xFF2ECC71) else Color(0xFFE74C3C),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
