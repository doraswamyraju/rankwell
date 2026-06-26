package com.reviewflow.ui.businessowner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reviewflow.ui.reviews.CustomerReviewScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessOwnerShell(
    initialBusinessName: String,
    initialReviewUrl: String,
    onLogOut: () -> Unit
) {
    var activeSubScreen by remember { mutableStateOf("dashboard") } // dashboard, campaigns, contacts, subscription, designer, builder, shop
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Light Mode Palette
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val accentColor = Color(0xFFFF8C00)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = lightSurface
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "RankWell Business",
                    color = lightTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(16.dp)
                )
                Divider(color = Color.LightGray)

                NavigationDrawerItem(
                    label = { Text("Dashboard Stats", color = lightTextPrimary) },
                    selected = activeSubScreen == "dashboard",
                    onClick = {
                        activeSubScreen = "dashboard"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Review Campaigns", color = lightTextPrimary) },
                    selected = activeSubScreen == "campaigns",
                    onClick = {
                        activeSubScreen = "campaigns"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Landing Page Builder", color = lightTextPrimary) },
                    selected = activeSubScreen == "builder",
                    onClick = {
                        activeSubScreen = "builder"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Edit, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Customer Contacts", color = lightTextPrimary) },
                    selected = activeSubScreen == "contacts",
                    onClick = {
                        activeSubScreen = "contacts"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Shop Add-ons / NFC", color = lightTextPrimary) },
                    selected = activeSubScreen == "shop",
                    onClick = {
                        activeSubScreen = "shop"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Subscription Billing", color = lightTextPrimary) },
                    selected = activeSubScreen == "subscription",
                    onClick = {
                        activeSubScreen = "subscription"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Log Out", color = Color.Red) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogOut()
                    },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Log Out", tint = Color.Red) },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (activeSubScreen != "designer" && activeSubScreen != "builder" && activeSubScreen != "shop") {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (activeSubScreen) {
                                    "dashboard" -> "Business Dashboard"
                                    "campaigns" -> "QR Review Campaigns"
                                    "contacts" -> "Target Contacts"
                                    "subscription" -> "Billing Overview"
                                    else -> "RankWell"
                                },
                                color = lightTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Open Sidebar", tint = lightTextPrimary)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = lightSurface)
                    )
                }
            },
            bottomBar = {
                if (activeSubScreen != "designer" && activeSubScreen != "builder" && activeSubScreen != "shop") {
                    NavigationBar(
                        containerColor = lightSurface
                    ) {
                        NavigationBarItem(
                            selected = activeSubScreen == "dashboard",
                            onClick = { activeSubScreen = "dashboard" },
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Stats", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = accentColor,
                                selectedTextColor = accentColor,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                        NavigationBarItem(
                            selected = activeSubScreen == "campaigns",
                            onClick = { activeSubScreen = "campaigns" },
                            icon = { Icon(Icons.Default.List, contentDescription = "Campaigns") },
                            label = { Text("QR codes", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = accentColor,
                                selectedTextColor = accentColor,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                        NavigationBarItem(
                            selected = activeSubScreen == "contacts",
                            onClick = { activeSubScreen = "contacts" },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Contacts") },
                            label = { Text("Contacts", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = accentColor,
                                selectedTextColor = accentColor,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (activeSubScreen == "designer" || activeSubScreen == "builder" || activeSubScreen == "shop" || activeSubScreen == "customer_review") PaddingValues(0.dp) else innerPadding)
                    .background(lightBg)
            ) {
                when (activeSubScreen) {
                    "dashboard" -> BusinessDashboardScreen(
                        initialBusinessName = initialBusinessName,
                        initialReviewUrl = initialReviewUrl,
                        onNavigateToCampaigns = { activeSubScreen = "campaigns" },
                        onNavigateToContacts = { activeSubScreen = "contacts" },
                        onNavigateToBuilder = { activeSubScreen = "builder" },
                        onPreviewLanding = { activeSubScreen = "customer_review" }
                    )
                    "campaigns" -> CampaignsScreen(
                        onNavigateToDesigner = { activeSubScreen = "designer" }
                    )
                    "contacts" -> ContactsScreen()
                    "subscription" -> SubscriptionScreen(
                        onNavigateToShop = { activeSubScreen = "shop" }
                    )
                    "designer" -> QrDesignerScreen(
                        reviewUrl = initialReviewUrl,
                        onBack = { activeSubScreen = "campaigns" }
                    )
                    "builder" -> LandingPageBuilderScreen(
                        onBack = { activeSubScreen = "dashboard" }
                    )
                    "shop" -> ShopAddonsScreen(
                        onBack = { activeSubScreen = "subscription" }
                    )
                    "customer_review" -> CustomerReviewScreen(
                        onBack = { activeSubScreen = "dashboard" }
                    )
                }
            }
        }
    }
}
