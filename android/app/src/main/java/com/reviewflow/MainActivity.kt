package com.reviewflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.reviewflow.ui.auth.AuthScreen
import com.reviewflow.ui.onboarding.OnboardingScreen
import com.reviewflow.ui.dashboard.AnalyticsDashboardScreen
import com.reviewflow.ui.superadmin.SuperAdminShell

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
                    onComplete = { currentScreen = "super_admin_shell" } // Route to Super Admin dashboard to review modules
                )
                "super_admin_shell" -> SuperAdminShell()
                "dashboard" -> AnalyticsDashboardScreen()
            }
        }
    }
}
