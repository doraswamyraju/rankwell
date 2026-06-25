package com.reviewflow.ui.superadmin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperAdminShell() {
    var activeSubScreen by remember { mutableStateOf("dashboard") } // dashboard, plans, users, logs
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Light Theme Palette
    val lightBg = Color(0xFFF8F9FA)
    val lightSurface = Color.White
    val lightTextPrimary = Color(0xFF212529)
    val accentColor = Color(0xFFFF8C00) // RankWell Orange

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = lightSurface
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "RankWell Admin",
                    color = lightTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(16.dp)
                )
                Divider(color = Color.LightGray)
                
                NavigationDrawerItem(
                    label = { Text("System Overview", color = lightTextPrimary) },
                    selected = activeSubScreen == "dashboard",
                    onClick = {
                        activeSubScreen = "dashboard"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Dynamic Pricing Engine", color = lightTextPrimary) },
                    selected = activeSubScreen == "plans",
                    onClick = {
                        activeSubScreen = "plans"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Users Directory", color = lightTextPrimary) },
                    selected = activeSubScreen == "users",
                    onClick = {
                        activeSubScreen = "users"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Security Audits", color = lightTextPrimary) },
                    selected = activeSubScreen == "logs",
                    onClick = {
                        activeSubScreen = "logs"
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null, tint = accentColor) },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when(activeSubScreen) {
                                "dashboard" -> "Super Admin Portal"
                                "plans" -> "Plans & Pricing Editor"
                                "users" -> "Accounts Management"
                                "logs" -> "System Security Logs"
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = lightSurface
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = lightSurface
                ) {
                    NavigationBarItem(
                        selected = activeSubScreen == "dashboard",
                        onClick = { activeSubScreen = "dashboard" },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Overview") },
                        label = { Text("Overview", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = accentColor,
                            selectedTextColor = accentColor,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                    NavigationBarItem(
                        selected = activeSubScreen == "plans",
                        onClick = { activeSubScreen = "plans" },
                        icon = { Icon(Icons.Default.List, contentDescription = "Plans") },
                        label = { Text("Plans", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = accentColor,
                            selectedTextColor = accentColor,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                    NavigationBarItem(
                        selected = activeSubScreen == "users",
                        onClick = { activeSubScreen = "users" },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Users") },
                        label = { Text("Users", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = accentColor,
                            selectedTextColor = accentColor,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(lightBg)
            ) {
                when (activeSubScreen) {
                    "dashboard" -> SuperAdminDashboardScreenLight(
                        onNavigateToPlans = { activeSubScreen = "plans" },
                        onNavigateToUsers = { activeSubScreen = "users" },
                        onNavigateToAuditLogs = { activeSubScreen = "logs" }
                    )
                    "plans" -> PlansPricingScreenLight()
                    "users" -> UsersManagementScreenLight()
                    "logs" -> AuditLogsScreenLight()
                }
            }
        }
    }
}
