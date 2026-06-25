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
                    onAuthSuccess = { role ->
                        if (role == "admin") {
                            currentScreen = "super_admin_shell"
                        } else {
                            currentScreen = "onboarding"
                        }
                    }
                )
                "onboarding" -> OnboardingScreen(
                    onComplete = { currentScreen = "dashboard" }
                )
                "super_admin_shell" -> SuperAdminShell(
                    onLogOut = { currentScreen = "auth" }
                )
                "dashboard" -> AnalyticsDashboardScreen()
            }
        }
    }
}
