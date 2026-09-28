package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.SafeErrorContainer
import com.example.ui.theme.SafeErrorRed
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.VerifiedGreenContainer
import com.example.ui.theme.WarmCoral
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.MaitryViewModel

@Composable
fun AdminPanelScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val config by viewModel.platformConfig.collectAsState()
    val reports by viewModel.safetyReports.collectAsState()
    val matches by viewModel.curatedMatches.collectAsState()
    val invoices by viewModel.invoices.collectAsState()

    var annualPriceText by remember(config) { mutableStateOf((config?.annualPrice ?: 999).toString()) }
    var sixMonthPriceText by remember(config) { mutableStateOf((config?.sixMonthPrice ?: 599).toString()) }
    var monthlyPriceText by remember(config) { mutableStateOf((config?.monthlyPrice ?: 199).toString()) }

    val totalRevenue = invoices.sumOf { it.totalAmount }
    val subscriberCount = invoices.size.coerceAtLeast(1)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
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
                    text = "Platform Admin Panel",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
            }
        }

        // Metrics Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    title = "Revenue",
                    value = "₹${totalRevenue.toInt()}",
                    subtitle = "18% GST Inclusive",
                    icon = Icons.Default.CurrencyRupee,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Subscribers",
                    value = "$subscriberCount",
                    subtitle = "Paid Only • 0 Free",
                    icon = Icons.Default.People,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Pending Reports",
                    value = "${reports.count { it.status == "SUBMITTED_TO_24H_QUEUE" }}",
                    subtitle = "24-hr Queue",
                    icon = Icons.Default.Flag,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Pricing Configuration Section
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CurrencyRupee, contentDescription = null, tint = DeepTeal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dynamic Pricing Configuration",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = DeepTeal
                        )
                    }
                    Text(
                        text = "Update plan pricing in real time (GST inclusive)",
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = annualPriceText,
                            onValueChange = { annualPriceText = it },
                            label = { Text("Annual (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = sixMonthPriceText,
                            onValueChange = { sixMonthPriceText = it },
                            label = { Text("6-Month (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = monthlyPriceText,
                            onValueChange = { monthlyPriceText = it },
                            label = { Text("1-Month (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val a = annualPriceText.toIntOrNull() ?: 999
                            val s = sixMonthPriceText.toIntOrNull() ?: 599
                            val m = monthlyPriceText.toIntOrNull() ?: 199
                            viewModel.updatePricing(a, s, m)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save New Plan Pricing")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Moderation Queue (IT Rules 2021)
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = SafeErrorRed)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "24-Hour Moderation Queue",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimaryDark
                            )
                        }
                        Surface(
                            color = SafeErrorContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${reports.size} Reported",
                                color = SafeErrorRed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Review flags filed by users in safe chat per IT Rules 2021 mandates.",
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (reports.isEmpty()) {
                        Text(
                            text = "✓ Moderation queue is clean. Zero pending complaints.",
                            fontSize = 12.sp,
                            color = VerifiedGreen
                        )
                    } else {
                        reports.forEach { report ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SurfaceVariantWarm,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Target: ${report.reportedName}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = TextPrimaryDark
                                        )
                                        Text(
                                            text = report.status,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (report.status.contains("ACTIONED")) SafeErrorRed else DeepTeal
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Reason: ${report.reason}", fontSize = 11.sp, color = SafeErrorRed)
                                    if (report.details.isNotBlank()) {
                                        Text("Details: ${report.details}", fontSize = 11.sp, color = TextSecondaryMuted)
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { viewModel.resolveModerationReport(report.id, "BANNED_USER") },
                                            colors = ButtonDefaults.buttonColors(containerColor = SafeErrorRed),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text("Ban User", fontSize = 11.sp)
                                        }
                                        OutlinedButton(
                                            onClick = { viewModel.resolveModerationReport(report.id, "DISMISSED") },
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text("Dismiss", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Test Feature: Simulate Subscription Expiry
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCoralContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LockClock, contentDescription = null, tint = WarmCoral)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Test Subscription Expiry & Paywall Lock",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF6B1D0E)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Simulate an expired annual plan to test the renewal lock screen and 90-day match preservation notice.",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = Color(0xFF8B2B19)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.simulateExpiry() },
                        colors = ButtonDefaults.buttonColors(containerColor = WarmCoral),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Simulate Expiry Now")
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        modifier = modifier.border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(icon, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = DeepTeal)
            Text(title, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = TextPrimaryDark)
            Text(subtitle, fontSize = 9.sp, color = TextSecondaryMuted)
        }
    }
}
