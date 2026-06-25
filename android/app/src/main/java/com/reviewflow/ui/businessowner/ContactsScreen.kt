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
fun ContactsScreen() {
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

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
            Text("Contacts Directory", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = { /* Action to import CSV */ },
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Import CSV / Excel", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ContactItemLight(name = "David Miller", email = "david@miller.com", phone = "+1 248-129-4822", tag = "Regular")
        ContactItemLight(name = "Sarah Connor", email = "sarah@cyber.com", phone = "+1 310-854-1929", tag = "VIP")
        ContactItemLight(name = "Bruce Wayne", email = "bruce@waynecorp.com", phone = "+1 917-843-9182", tag = "VIP")
        ContactItemLight(name = "Jane Watson", email = "jane@watson.com", phone = "+1 650-928-1122", tag = "First Visit")
    }
}

@Composable
fun ContactItemLight(
    name: String,
    email: String,
    phone: String,
    tag: String
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
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(name, color = Color(0xFF212529), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(email, color = Color(0xFF6C757D), fontSize = 12.sp)
                Text(phone, color = Color.Gray, fontSize = 12.sp)
            }

            Box(
                modifier = Modifier
                    .background(Color(0xFFFFF3CD), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(tag, color = Color(0xFF856404), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
