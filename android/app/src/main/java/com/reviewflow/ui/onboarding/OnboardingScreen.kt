package com.reviewflow.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reviewflow.network.ApiService
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onComplete: (String, String) -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var googleMapsUrl by remember { mutableStateOf("") }
    var placeId by remember { mutableStateOf("") }
    var generatedReviewUrl by remember { mutableStateOf("") }
    var isResolving by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var isPreviewActive by remember { mutableStateOf(false) }

    var selectedTab by remember { mutableStateOf(0) } // 0 = Search, 1 = Paste Link
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<JSONObject>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Add Your Business",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Search your business profile or paste your Google Maps link.",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Segmented Picker Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = { selectedTab = 0 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 0) Color(0xFFFF8C00) else Color.Transparent
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text("Search Profile", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { selectedTab = 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 1) Color(0xFFFF8C00) else Color.Transparent
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text("Paste Link", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (selectedTab == 0) {
                // Search Tab UI
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(16.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("BUSINESS NAME OR KEYWORDS", color = Color(0xFFFF8C00), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("e.g. Starbucks Market St", color = Color.Gray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFFF8C00),
                                unfocusedBorderColor = Color.DarkGray
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                if (searchQuery.isNotEmpty()) {
                                    isSearching = true
                                    scope.launch {
                                        try {
                                            val array = ApiService.searchGooglePlaces(searchQuery)
                                            val list = mutableListOf<JSONObject>()
                                            for (i in 0 until array.length()) {
                                                list.add(array.getJSONObject(i))
                                            }
                                            searchResults = list
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        } finally {
                                            isSearching = false
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                            shape = RoundedCornerShape(8.dp),
                            enabled = searchQuery.isNotEmpty() && !isSearching
                        ) {
                            if (isSearching) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp))
                            } else {
                                Text("Search")
                            }
                        }
                    }

                    if (searchResults.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("RESULTS (ESTABLISHMENTS ONLY)", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            searchResults.forEach { item ->
                                val name = item.optString("name")
                                val addressVal = item.optString("formattedAddress")
                                
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                                        .clickable {
                                            businessName = name
                                            address = addressVal
                                            googleMapsUrl = item.optString("googleMapsUrl")
                                            placeId = item.optString("placeId")
                                            generatedReviewUrl = item.optString("googleReviewUrl")
                                            isPreviewActive = true
                                        }
                                        .padding(12.dp)
                                ) {
                                    Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(addressVal, color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            } else {
                // Paste Link Tab UI
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(16.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = businessName,
                        onValueChange = { businessName = it },
                        label = { Text("Business Name", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Address (Optional)", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF8C00),
                            unfocusedBorderColor = Color.DarkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = googleMapsUrl,
                            onValueChange = { googleMapsUrl = it },
                            label = { Text("Google Maps Link", color = Color.Gray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFFF8C00),
                                unfocusedBorderColor = Color.DarkGray
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                isResolving = true
                                // Mock resolving using Space Needle
                                placeId = "ChIJyTMQ5lhGkFQR8h5s5wZyC-c"
                                generatedReviewUrl = "https://search.google.com/local/writereview?placeid=$placeId"
                                isResolving = false
                                isPreviewActive = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            if (isResolving) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text("Verify")
                            }
                        }
                    }
                }
            }

            if (isPreviewActive) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0x11FF8C00))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Selected Business Details", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text("Name:", color = Color.Gray, fontSize = 11.sp)
                        Text(businessName, color = Color.White, fontSize = 14.sp)

                        if (address.isNotEmpty()) {
                            Text("Address:", color = Color.Gray, fontSize = 11.sp)
                            Text(address, color = Color.White, fontSize = 13.sp)
                        }

                        Text("Resolved Place ID:", color = Color.Gray, fontSize = 11.sp)
                        Text(placeId, color = Color.Yellow, fontWeight = FontWeight.Bold, fontSize = 13.sp)

                        Text("Google Review URL:", color = Color.Gray, fontSize = 11.sp)
                        Text(generatedReviewUrl, color = Color(0xFFFF8C00), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isSaving = true
                    scope.launch {
                        try {
                            ApiService.onboardBusiness(businessName, address, googleMapsUrl)
                            onComplete(businessName, generatedReviewUrl)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            isSaving = false
                        }
                    }
                },
                enabled = businessName.isNotEmpty() && !isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4500))
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Onboard Business", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
