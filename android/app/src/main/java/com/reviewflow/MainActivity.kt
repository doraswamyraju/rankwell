package com.reviewflow

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.reviewflow.ui.auth.AuthScreen
import com.reviewflow.ui.onboarding.OnboardingScreen
import com.reviewflow.ui.dashboard.AnalyticsDashboardScreen
import com.reviewflow.ui.superadmin.SuperAdminShell
import com.reviewflow.ui.businessowner.BusinessOwnerShell

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sharedPrefs = getSharedPreferences("rankwell_prefs", Context.MODE_PRIVATE)

        setContent {
            var currentScreen by remember { mutableStateOf("auth") }
            var isOnboarded by remember { mutableStateOf(sharedPrefs.getBoolean("is_onboarded", false)) }
            var savedBusinessName by remember { mutableStateOf(sharedPrefs.getString("business_name", "Blue Bottle Coffee") ?: "Blue Bottle Coffee") }
            var savedReviewUrl by remember { mutableStateOf(sharedPrefs.getString("review_url", "https://search.google.com/local/writereview?placeid=ChIJu3nS6hS8j4ARU_j6X-JmRzY") ?: "https://search.google.com/local/writereview?placeid=ChIJu3nS6hS8j4ARU_j6X-JmRzY") }
            
            when (currentScreen) {
                "auth" -> AuthScreen(
                    onAuthSuccess = { role ->
                        if (role == "admin") {
                            currentScreen = "super_admin_shell"
                        } else {
                            if (isOnboarded) {
                                currentScreen = "dashboard"
                            } else {
                                currentScreen = "onboarding"
                            }
                        }
                    }
                )
                "onboarding" -> OnboardingScreen(
                    onComplete = { name, reviewUrl ->
                        sharedPrefs.edit()
                            .putBoolean("is_onboarded", true)
                            .putString("business_name", name)
                            .putString("review_url", reviewUrl)
                            .apply()
                        isOnboarded = true
                        savedBusinessName = name
                        savedReviewUrl = reviewUrl
                        currentScreen = "dashboard"
                    }
                )
                "super_admin_shell" -> SuperAdminShell(
                    onLogOut = { currentScreen = "auth" }
                )
                "dashboard" -> BusinessOwnerShell(
                    initialBusinessName = savedBusinessName,
                    initialReviewUrl = savedReviewUrl,
                    onLogOut = {
                        // Clear session state but retain onboarding
                        currentScreen = "auth"
                    }
                )
            }
        }
    }
}
