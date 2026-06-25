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
fun UsersManagementScreen(
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
                Text("User Accounts", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            // Search/Filters (Visual Mock)
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search Users / Filter by Role", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Users List Mock
            Text("REGISTERED USER DIRECTORY", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)

            UserListItem(name = "Rajugari Ventures", email = "admin@rgv.com", role = "Agency", status = "Active")
            UserListItem(name = "John Doe Cafe", email = "john@cafe.com", role = "Business Owner", status = "Active")
            UserListItem(name = "Staff member A", email = "staff1@cafe.com", role = "Staff", status = "Active")
            UserListItem(name = "Blocked user", email = "spam@domain.com", role = "Business Owner", status = "Suspended")
        }
    }
}

@Composable
fun UserListItem(
    name: String,
    email: String,
    role: String,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(email, color = Color.Gray, fontSize = 12.sp)
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

private val Float.opacity: Float
    get() = this
