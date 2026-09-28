package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.ui.screens.AccountSafetyTab
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.AgeGateScreen
import com.example.ui.screens.ExpiryLockScreen
import com.example.ui.screens.HowItWorksScreen
import com.example.ui.screens.InvoiceDetailDialog
import com.example.ui.screens.LandingScreen
import com.example.ui.screens.LegalDocsScreen
import com.example.ui.screens.MainAppScreen
import com.example.ui.screens.PaywallScreen
import com.example.ui.screens.PhoneAuthScreen
import com.example.ui.screens.ProfileSetupScreen
import com.example.ui.screens.SafetyPoliciesScreen
import com.example.ui.screens.SubscriptionPlansScreen
import com.example.ui.screens.UserProfileDetailScreen
import com.example.ui.screens.VerificationScreen
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.MaitryTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel: MaitryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaitryTheme {
                val currentScreen by viewModel.currentScreen.collectAsState()
                val selectedInvoice by viewModel.selectedInvoice.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    viewModel.toastEvent.collectLatest { msg ->
                        snackbarHostState.showSnackbar(
                            message = msg,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CreamBackground)
                            .padding(innerPadding)
                    ) {
                        when (currentScreen) {
                            AppScreen.LANDING -> {
                                LandingScreen(viewModel = viewModel)
                            }
                            AppScreen.HOW_IT_WORKS -> {
                                BackHandler { viewModel.navigateBack() }
                                HowItWorksScreen(viewModel = viewModel)
                            }
                            AppScreen.SAFETY_POLICIES -> {
                                BackHandler { viewModel.navigateBack() }
                                SafetyPoliciesScreen(viewModel = viewModel)
                            }
                            AppScreen.LEGAL_DOCS -> {
                                BackHandler { viewModel.navigateBack() }
                                LegalDocsScreen(viewModel = viewModel)
                            }
                            AppScreen.AGE_GATE -> {
                                BackHandler { viewModel.navigateBack() }
                                AgeGateScreen(viewModel = viewModel)
                            }
                            AppScreen.PHONE_AUTH -> {
                                BackHandler { viewModel.navigateBack() }
                                PhoneAuthScreen(viewModel = viewModel)
                            }
                            AppScreen.PROFILE_SETUP -> {
                                BackHandler { viewModel.navigateBack() }
                                ProfileSetupScreen(viewModel = viewModel)
                            }
                            AppScreen.VERIFICATION -> {
                                BackHandler { viewModel.navigateBack() }
                                VerificationScreen(viewModel = viewModel)
                            }
                            AppScreen.PAYWALL -> {
                                BackHandler { viewModel.navigateBack() }
                                PaywallScreen(viewModel = viewModel)
                            }
                            AppScreen.MAIN_APP -> {
                                MainAppScreen(viewModel = viewModel)
                            }
                            AppScreen.EXPIRY_LOCK -> {
                                ExpiryLockScreen(viewModel = viewModel)
                            }
                            AppScreen.ADMIN_PANEL -> {
                                BackHandler { viewModel.navigateBack() }
                                AdminPanelScreen(viewModel = viewModel)
                            }
                            AppScreen.USER_PROFILE_DETAIL -> {
                                BackHandler { viewModel.navigateBack() }
                                UserProfileDetailScreen(
                                    onBackClick = { viewModel.navigateBack() },
                                    onEditProfileClick = { viewModel.navigateTo(AppScreen.PROFILE_SETUP) }
                                )
                            }
                            AppScreen.SUBSCRIPTION_PLANS -> {
                                BackHandler { viewModel.navigateBack() }
                                SubscriptionPlansScreen(
                                    onPlanChosen = { plan ->
                                        viewModel.processPayment(
                                            planId = plan.planId,
                                            paymentMethod = "UPI (Instant Activation)"
                                        )
                                    },
                                    onBackClick = { viewModel.navigateBack() }
                                )
                            }
                        }

                        // GST Invoice Dialog overlay if user views an invoice
                        if (selectedInvoice != null) {
                            InvoiceDetailDialog(
                                invoice = selectedInvoice!!,
                                onDismiss = { viewModel.closeInvoice() }
                            )
                        }
                    }
                }
            }
        }
    }
}
