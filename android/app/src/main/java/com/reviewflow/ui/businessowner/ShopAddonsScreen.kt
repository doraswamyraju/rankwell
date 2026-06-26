package com.reviewflow.ui.businessowner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopAddonsScreen(
    onBack: () -> Unit
) {
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

    var selectedTab by remember { mutableStateOf(0) } // 0: Credits, 1: NFC Products, 2: Invoices

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop Add-ons & Credits", color = lightTextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = lightTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lightSurface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(lightBg)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = lightSurface,
                contentColor = accentColor
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Credits", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("NFC Cards", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                    Text("Invoices", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        // Credits Packages
                        Text("RECHARGE WALLET & AI/WHATSAPP CREDITS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        
                        CreditPackRow(title = "+ 500 AI Reviews Credits", price = "$15", detail = "Draft reviews with Smart review generator")
                        CreditPackRow(title = "+ 1,000 AI Reviews Credits", price = "$25", detail = "Most popular for growing brands")
                        CreditPackRow(title = "+ 2,000 WhatsApp SMS Credits", price = "$39", detail = "Trigger review requests directly via chat")
                        CreditPackRow(title = "+ 5,000 WhatsApp SMS Credits", price = "$89", detail = "High volume campaigns & broadcasts")
                    }
                    1 -> {
                        // NFC Review Cards Shop
                        Text("PHYSICAL NFC & QR PRODUCTS", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        
                        NfcProductCard(title = "NFC Matte PVC Review Card", price = "$19", desc = "Durable card, tap to open review landing page")
                        NfcProductCard(title = "Premium Metal NFC Card", price = "$39", desc = "Engraved metal design, wow premium customers")
                        NfcProductCard(title = "NFC Display Standee", price = "$29", desc = "Perfect for billing counter / tables")
                        NfcProductCard(title = "NFC Window Stickers (Pack of 5)", price = "$12", desc = "Stick to glass doors, quick tap to review")
                    }
                    2 -> {
                        // Invoice History
                        Text("INVOICES & TRANSACTION LEDGER", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        
                        InvoiceItemRow(invoiceNo = "INV-2026-009", date = "June 20, 2026", amount = "$49.00", status = "Paid")
                        InvoiceItemRow(invoiceNo = "INV-2026-008", date = "May 20, 2026", amount = "$49.00", status = "Paid")
                        InvoiceItemRow(invoiceNo = "INV-2026-004", date = "April 29, 2026", amount = "$15.00", status = "Refunded")
                        InvoiceItemRow(invoiceNo = "INV-2026-001", date = "April 20, 2026", amount = "$49.00", status = "Paid")
                    }
                }
            }
        }
    }
}

@Composable
fun CreditPackRow(title: String, price: String, detail: String) {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF212529), fontSize = 15.sp)
                Text(detail, color = Color(0xFF6C757D), fontSize = 12.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Buy $price", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun NfcProductCard(title: String, price: String, desc: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF212529), fontSize = 16.sp)
                Text(price, fontWeight = FontWeight.Black, color = Color(0xFFFF8C00), fontSize = 16.sp)
            }
            Text(desc, color = Color(0xFF6C757D), fontSize = 12.sp)
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF212529)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add to Cart", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun InvoiceItemRow(invoiceNo: String, date: String, amount: String, status: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(invoiceNo, fontWeight = FontWeight.Bold, color = Color(0xFF212529), fontSize = 14.sp)
                Text(date, color = Color(0xFF6C757D), fontSize = 11.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(amount, fontWeight = FontWeight.Bold, color = Color(0xFF212529), fontSize = 14.sp)
                Box(
                    modifier = Modifier
                        .background(
                            if (status == "Paid") Color(0xFFD4EDDA) else Color(0xFFF8D7DA),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        status,
                        color = if (status == "Paid") Color(0xFF155724) else Color(0xFF721C24),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
