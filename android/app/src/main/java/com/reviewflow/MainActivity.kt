package com.reviewflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.reviewflow.ui.auth.AuthScreen
import com.reviewflow.ui.onboarding.OnboardingScreen
import com.reviewflow.ui.dashboard.AnalyticsDashboardScreen
import com.reviewflow.ui.superadmin.SuperAdminDashboardScreen
import com.reviewflow.ui.superadmin.PlansPricingScreen
import com.reviewflow.ui.superadmin.UsersManagementScreen
import com.reviewflow.ui.superadmin.AuditLogsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf("auth") }
            
            when (currentScreen) {
                "auth" -> AuthScreen(
                    onAuthSuccess = { currentScreen = "onboarding" }
                )
                "onboarding" -> OnboardingScreen(
                    onComplete = { currentScreen = "super_admin_dashboard" } // Route to Super Admin dashboard to review modules
                )
                "super_admin_dashboard" -> SuperAdminDashboardScreen(
                    onNavigateToPlans = { currentScreen = "super_admin_plans" },
                    onNavigateToUsers = { currentScreen = "super_admin_users" },
                    onNavigateToAuditLogs = { currentScreen = "super_admin_logs" }
                )
                "super_admin_plans" -> PlansPricingScreen(
                    onBack = { currentScreen = "super_admin_dashboard" }
                )
                "super_admin_users" -> UsersManagementScreen(
                    onBack = { currentScreen = "super_admin_dashboard" }
                )
                "super_admin_logs" -> AuditLogsScreen(
                    onBack = { currentScreen = "super_admin_dashboard" }
                )
                "dashboard" -> AnalyticsDashboardScreen()
            }
        }
    }
}
