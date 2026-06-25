package com.reviewflow.ui.campaigns

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reviewflow.utils.QrGeneratorService
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrDesignerScreen() {
    val context = LocalContext.current
    var campaignName by remember { mutableStateOf("Summer Campaign") }
    var ctaText by remember { mutableStateOf("Scan to Review Us!") }
    var foregroundColor by remember { mutableStateOf(android.graphics.Color.BLACK) }
    var backgroundColor by remember { mutableStateOf(android.graphics.Color.WHITE) }
    
    val targetLink = "https://search.google.com/local/writereview?placeid=ChIJu46S-uB-hYGRwT3QyP6Wj2Y"
    val qrGenerator = remember { QrGeneratorService() }
    
    val qrBitmap = remember(foregroundColor, backgroundColor) {
        qrGenerator.generateQRCode(targetLink, foregroundColor = foregroundColor, backgroundColor = backgroundColor)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F0F1A), Color(0xFF1E102F))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "QR Designer",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            // QR Preview
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "QR Code Preview",
                    modifier = Modifier
                        .size(240.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                )

                if (ctaText.isNotEmpty()) {
                    Text(
                        text = ctaText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color(0xFFFF8C00), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Configuration Form
            OutlinedTextField(
                value = campaignName,
                onValueChange = { campaignName = it },
                label = { Text("Campaign Name", color = Color.Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFFF8C00),
                    unfocusedBorderColor = Color.DarkGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ctaText,
                onValueChange = { ctaText = it },
                label = { Text("Call to Action", color = Color.Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFFF8C00),
                    unfocusedBorderColor = Color.DarkGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Quick Color Presets
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        foregroundColor = android.graphics.Color.BLUE
                        backgroundColor = android.graphics.Color.WHITE
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Blue QR", color = Color.White)
                }

                Button(
                    onClick = {
                        foregroundColor = android.graphics.Color.BLACK
                        backgroundColor = android.graphics.Color.WHITE
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Classic", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Share & Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        // Save bitmap to file and trigger intent share
                        try {
                            val cachePath = File(context.cacheDir, "images")
                            cachePath.mkdirs()
                            val stream = FileOutputStream("$cachePath/qr_share.png")
                            qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            stream.close()

                            val imageFile = File(cachePath, "qr_share.png")
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "image/png"
                                putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile))
                            }
                            context.startActivity(Intent.createChooser(intent, "Share QR Code"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Share", color = Color.White)
                }

                Button(
                    onClick = { /* Save to Database */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4500)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save", color = Color.White)
                }
            }
        }
    }
}
