package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.DeepTealDark
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.VerifiedGreenContainer
import com.example.ui.theme.WarmCoral
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel

@Composable
fun PaywallScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val selectedPlanId by viewModel.selectedPlanId.collectAsState()
    val config by viewModel.platformConfig.collectAsState()

    val annualPrice = config?.annualPrice ?: 999
    val sixMonthPrice = config?.sixMonthPrice ?: 599
    val monthlyPrice = config?.monthlyPrice ?: 199

    var showPaymentModal by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf("UPI (GPay / PhonePe / Paytm)") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DeepTeal)
                }
                Text(
                    text = "Subscription Paywall",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
            }
        }

        // Lock Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCoralContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = WarmCoral,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Maitry is 100% Subscription-Only",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF6B1D0E)
                        )
                        Text(
                            text = "There is no free tier. Subscribing unlocks full access to browse daily curated matches, express interest, and chat safely.",
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = Color(0xFF8B2B19)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Choose Your Membership",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DeepTeal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "One-time payment • NO auto-debit • GST tax invoice provided",
                fontSize = 12.sp,
                color = TextSecondaryMuted,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Plan 1: Annual (Flagship - ₹999)
        item {
            val isSelected = selectedPlanId == "ANNUAL_999"
            PlanCard(
                title = "Annual Full Access",
                tagline = "Best Value for Genuine Friendship",
                duration = "12 Months (365 Days)",
                priceDisplay = "₹$annualPrice",
                pricePerMonth = "₹${annualPrice / 12}/month",
                isRecommended = true,
                isSelected = isSelected,
                onClick = { viewModel.selectedPlanId.value = "ANNUAL_999" },
                gstDetails = "Base: ₹846.61 + 18% GST: ₹152.39"
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Plan 2: 6 Months (₹599)
        item {
            val isSelected = selectedPlanId == "SIX_MONTH_599"
            PlanCard(
                title = "6-Month Membership",
                tagline = "Sufficient time to build a genuine bond",
                duration = "6 Months (180 Days)",
                priceDisplay = "₹$sixMonthPrice",
                pricePerMonth = "₹${sixMonthPrice / 6}/month",
                isRecommended = false,
                isSelected = isSelected,
                onClick = { viewModel.selectedPlanId.value = "SIX_MONTH_599" },
                gstDetails = "Base: ₹507.63 + 18% GST: ₹91.37"
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Plan 3: 1 Month (₹199)
        item {
            val isSelected = selectedPlanId == "MONTHLY_199"
            PlanCard(
                title = "1-Month Starter Membership",
                tagline = "Experience the safe Maitry circle",
                duration = "1 Month (30 Days)",
                priceDisplay = "₹$monthlyPrice",
                pricePerMonth = "₹$monthlyPrice/month",
                isRecommended = false,
                isSelected = isSelected,
                onClick = { viewModel.selectedPlanId.value = "MONTHLY_199" },
                gstDetails = "Base: ₹168.64 + 18% GST: ₹30.36"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Included Features Checklist
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "What is included with your membership:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BenefitLine("Daily curated matches tailored to your age, intent & city")
                    BenefitLine("Mutual connection unlocking (only mutuals can chat)")
                    BenefitLine("Safe text-first chat with scam & external link filtering")
                    BenefitLine("Mutual opt-in photo exchange control")
                    BenefitLine("90-day match preservation if subscription ever lapses")
                    BenefitLine("Dedicated Grievance Redressal & 24-hr report queue")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Pay Button
        item {
            val activeAmount = when (selectedPlanId) {
                "SIX_MONTH_599" -> sixMonthPrice
                "MONTHLY_199" -> monthlyPrice
                else -> annualPrice
            }

            Button(
                onClick = { showPaymentModal = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarmCoral)
            ) {
                Icon(Icons.Default.CreditCard, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Pay ₹$activeAmount via Razorpay (GST Incl.)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "🔒 Secured by 256-bit encryption. Handled by Razorpay. We do NOT store card numbers.",
                fontSize = 11.sp,
                color = TextSecondaryMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 420.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Razorpay Indian Payment Modal
    if (showPaymentModal) {
        val activeAmount = when (selectedPlanId) {
            "SIX_MONTH_599" -> sixMonthPrice
            "MONTHLY_199" -> monthlyPrice
            else -> annualPrice
        }

        androidx.compose.ui.window.Dialog(onDismissRequest = { showPaymentModal = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Razorpay Secure Checkout", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = DeepTeal)
                            Text("Merchant: Maitry Digital Trust (maitry.co.in)", fontSize = 11.sp, color = TextSecondaryMuted)
                            Surface(
                                color = DeepTealContainer,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = "LIVE KEY: ${com.example.BuildConfig.RAZORPAY_KEY_ID.ifEmpty { "rzp_live_ThZb8wghApMABI" }}",
                                    fontSize = 10.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepTeal,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Surface(
                            color = VerifiedGreenContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "₹$activeAmount",
                                fontWeight = FontWeight.ExtraBold,
                                color = VerifiedGreen,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Select Payment Method:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextPrimaryDark)
                    Spacer(modifier = Modifier.height(8.dp))

                    val methods = listOf(
                        "UPI (GPay / PhonePe / Paytm / BHIM)",
                        "Debit / Credit Card (Visa, RuPay, Mastercard)",
                        "Netbanking (All Major Indian Banks)"
                    )

                    methods.forEach { method ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedPaymentMethod == method) DeepTealContainer else SurfaceVariantWarm,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedPaymentMethod = method }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedPaymentMethod == method,
                                    onClick = { selectedPaymentMethod = method },
                                    colors = RadioButtonDefaults.colors(selectedColor = DeepTeal)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = method,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedPaymentMethod == method) FontWeight.Bold else FontWeight.Normal,
                                    color = TextPrimaryDark
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        color = SurfaceVariantWarm,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "🧾 GST breakdown (18% inclusive):",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = DeepTeal
                            )
                            val base = Math.round((activeAmount / 1.18) * 100.0) / 100.0
                            val gst = Math.round((activeAmount - base) * 100.0) / 100.0
                            Text(
                                text = "Taxable Base: ₹$base | CGST+SGST (18%): ₹$gst | Total: ₹$activeAmount",
                                fontSize = 11.sp,
                                color = TextSecondaryMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showPaymentModal = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                showPaymentModal = false
                                viewModel.processPayment(selectedPlanId, selectedPaymentMethod)
                            },
                            modifier = Modifier.weight(1.4f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)
                        ) {
                            Text("Complete ₹$activeAmount", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanCard(
    title: String,
    tagline: String,
    duration: String,
    priceDisplay: String,
    pricePerMonth: String,
    isRecommended: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    gstDetails: String
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) DeepTealContainer else CreamSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 500.dp)
            .border(
                2.dp,
                if (isSelected) DeepTeal else if (isRecommended) WarmCoral else Color(0xFFE2E9EA),
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepTeal
                        )
                        if (isRecommended) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = WarmCoral,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "MOST POPULAR",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = tagline,
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = priceDisplay,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepTeal
                    )
                    Text(
                        text = pricePerMonth,
                        fontSize = 11.sp,
                        color = WarmCoral,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(0xFFDDE6E8))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "• $duration • $gstDetails",
                    fontSize = 11.sp,
                    color = TextSecondaryMuted
                )
                RadioButton(
                    selected = isSelected,
                    onClick = onClick,
                    colors = RadioButtonDefaults.colors(selectedColor = DeepTeal)
                )
            }
        }
    }
}

@Composable
private fun BenefitLine(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = VerifiedGreen,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            color = TextPrimaryDark
        )
    }
}
