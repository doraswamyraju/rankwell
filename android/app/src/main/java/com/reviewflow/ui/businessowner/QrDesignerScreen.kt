package com.reviewflow.ui.businessowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrDesignerScreen(
    reviewUrl: String,
    onBack: () -> Unit
) {
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val lightTextSecondary = Color(0xFF6C757D)
    val accentColor = Color(0xFFFF8C00)

    var qrShape by remember { mutableStateOf("Square") }
    var selectedColor by remember { mutableStateOf(Color(0xFF212529)) }
    var hasLogo by remember { mutableStateOf(true) }
    var borderFrame by remember { mutableStateOf("Scan Me") }
    var printQuality by remember { mutableStateOf(300f) }
    var downloadFormat by remember { mutableStateOf("PNG") }

    val qrBitmap = remember(reviewUrl) {
        QrCodeGenerator.generateQrBitmap(reviewUrl)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QR Campaign Designer", color = lightTextPrimary, fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // QR Code Preview Box
            Card(
                modifier = Modifier.fillMaxWidth().height(260.dp),
                colors = CardDefaults.cardColors(containerColor = lightSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Real QR code
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .border(
                                    width = 4.dp,
                                    color = selectedColor,
                                    shape = if (qrShape == "Rounded") RoundedCornerShape(24.dp) else RoundedCornerShape(0.dp)
                                )
                                .background(Color.White)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                if (qrBitmap != null) {
                                    Image(
                                        bitmap = qrBitmap.asImageBitmap(),
                                        contentDescription = "Google Review QR Code",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    CircularProgressIndicator(color = accentColor)
                                }

                                if (hasLogo) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(accentColor, RoundedCornerShape(4.dp))
                                            .border(1.5.dp, Color.White, RoundedCornerShape(4.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("RW", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = borderFrame.uppercase(),
                            color = selectedColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Shapes selector
            Text("QR CODE SHAPE", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("Square", "Rounded").forEach { shape ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (qrShape == shape) accentColor.copy(alpha = 0.15f) else Color.White,
                                RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.5.dp,
                                color = if (qrShape == shape) accentColor else Color.LightGray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { qrShape = shape }
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(shape, color = if (qrShape == shape) accentColor else lightTextPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Colors selector
            Text("THEME COLOR", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                listOf(
                    Color(0xFF212529),
                    Color(0xFF4A90E2),
                    Color(0xFF2ECC71),
                    Color(0xFFFF8C00),
                    Color(0xFFD0021B)
                ).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(color, RoundedCornerShape(22.dp))
                            .clickable { selectedColor = color }
                            .border(
                                width = 3.dp,
                                color = if (selectedColor == color) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(22.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedColor == color) {
                            Icon(Icons.Default.Done, contentDescription = "Selected", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            // Logo and Frame Toggles
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = lightSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Center Branding Logo", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Display RankWell logo in center", color = lightTextSecondary, fontSize = 12.sp)
                        }
                        Switch(
                            checked = hasLogo,
                            onCheckedChange = { hasLogo = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = accentColor, checkedTrackColor = accentColor.copy(alpha = 0.3f))
                        )
                    }

                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Frame CTA Text", color = lightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        OutlinedTextField(
                            value = borderFrame,
                            onValueChange = { borderFrame = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )
                    }
                }
            }

            // Export options
            Text("EXPORT QUALITY & FORMAT", color = lightTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = lightSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Print Resolution: ${printQuality.toInt()} DPI", color = lightTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Slider(
                        value = printQuality,
                        onValueChange = { printQuality = it },
                        valueRange = 72f..600f,
                        colors = SliderDefaults.colors(
                            thumbColor = accentColor,
                            activeTrackColor = accentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Download Format", color = lightTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("PNG", "SVG", "PDF", "EPS").forEach { format ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        if (downloadFormat == format) accentColor else Color(0xFFF1F3F5),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .clickable { downloadFormat = format }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = format,
                                    color = if (downloadFormat == format) Color.White else lightTextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Action Button
            Button(
                onClick = { onBack() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save QR Design & Finish", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
