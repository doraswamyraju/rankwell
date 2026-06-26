package com.reviewflow.ui.businessowner

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

data class CustomerContact(
    val name: String,
    val email: String,
    val phone: String,
    val tag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen() {
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

    val contactsList = remember {
        mutableStateListOf(
            CustomerContact("David Miller", "david@miller.com", "+1 248-129-4822", "Regular"),
            CustomerContact("Sarah Connor", "sarah@cyber.com", "+1 310-854-1929", "VIP"),
            CustomerContact("Bruce Wayne", "bruce@waynecorp.com", "+1 917-843-9182", "VIP"),
            CustomerContact("Jane Watson", "jane@watson.com", "+1 650-928-1122", "First Visit")
        )
    }

    var showAddContactDialog by remember { mutableStateOf(false) }
    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPhone by remember { mutableStateOf("") }
    var inputTag by remember { mutableStateOf("Regular") }

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
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { /* Import CSV mock */ },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Import", fontSize = 12.sp)
                }
                Button(
                    onClick = { showAddContactDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("+ Add", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        contactsList.forEach { contact ->
            ContactItemLight(
                name = contact.name,
                email = contact.email,
                phone = contact.phone,
                tag = contact.tag
            )
        }
    }

    if (showAddContactDialog) {
        AlertDialog(
            onDismissRequest = { showAddContactDialog = false },
            title = { Text("Add Customer Contact") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { inputName = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentColor)
                    )
                    OutlinedTextField(
                        value = inputEmail,
                        onValueChange = { inputEmail = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentColor)
                    )
                    OutlinedTextField(
                        value = inputPhone,
                        onValueChange = { inputPhone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentColor)
                    )

                    Text("Tag Segment", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Regular", "VIP", "First Visit").forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        if (inputTag == tag) accentColor.copy(alpha = 0.15f) else Color(0xFFF1F3F5),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .clickable { inputTag = tag }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tag,
                                    color = if (inputTag == tag) accentColor else lightTextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (inputName.isNotEmpty()) {
                            contactsList.add(0, CustomerContact(inputName, inputEmail, inputPhone, inputTag))
                            showAddContactDialog = false
                            inputName = ""
                            inputEmail = ""
                            inputPhone = ""
                            inputTag = "Regular"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    enabled = inputName.isNotEmpty()
                ) {
                    Text("Add Contact")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddContactDialog = false }) {
                    Text("Cancel")
                }
            }
        )
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

