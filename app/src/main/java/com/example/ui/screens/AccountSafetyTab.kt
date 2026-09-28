package com.example.ui.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.InvoiceEntity
import com.example.ui.components.AvatarPlaceholder
import com.example.ui.components.VerifiedBadge
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
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AccountSafetyTab(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsState()
    val invoices by viewModel.invoices.collectAsState()
    val config by viewModel.platformConfig.collectAsState()

    var showExportDialog by remember { mutableStateOf(false) }
    var exportJsonContent by remember { mutableStateOf("") }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    val daysLeft = if (user != null && user!!.subscriptionExpiryDate > System.currentTimeMillis()) {
        val diff = user!!.subscriptionExpiryDate - System.currentTimeMillis()
        (diff / (1000L * 60L * 60L * 24L)).toInt()
    } else 0

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "My Account & Safety",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
                Text(
                    text = "Subscription, privacy controls & legal protections",
                    fontSize = 12.sp,
                    color = TextSecondaryMuted
                )
            }
        }

        // Profile Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarPlaceholder(
                        name = user?.name ?: "Tara Sen",
                        size = 56,
                        backgroundColor = DeepTeal
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${user?.name ?: "Tara Sen"}, ${user?.age ?: 29}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = TextPrimaryDark
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            VerifiedBadge()
                        }
                        Text(
                            text = "${user?.phoneNumber ?: "+91 98201 23456"} • ${user?.city ?: "Mumbai"}",
                            fontSize = 12.sp,
                            color = TextSecondaryMuted
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Looking for: ${user?.intent ?: "Friendship leading to relationship"}",
                            fontSize = 11.sp,
                            color = WarmCoral,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Action to open UserProfileDetailScreen
                androidx.compose.material3.TextButton(
                    onClick = { viewModel.navigateTo(AppScreen.USER_PROFILE_DETAIL) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shield,
                        contentDescription = null,
                        tint = DeepTeal,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "View Full Profile (Photos, Bio & Interests)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepTeal
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Active Subscription Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DeepTealContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Active Membership",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = DeepTeal
                            )
                        }
                        Surface(
                            color = VerifiedGreenContainer,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "$daysLeft Days Left",
                                color = VerifiedGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = user?.planName ?: "Annual Full Access (Friendship First)",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = TextPrimaryDark
                    )

                    val expiryDateStr = if (user != null && user!!.subscriptionExpiryDate > 0) {
                        SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date(user!!.subscriptionExpiryDate))
                    } else "365 days from activation"

                    Text(
                        text = "Valid until $expiryDateStr • Strictly NO auto-renewal",
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = CreamSurface,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "ℹ️ Renewal reminders sent via SMS/Email at 15, 7, and 1 day before expiry. If expired, your matches are saved for 90 days.",
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = DeepTeal,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppScreen.SUBSCRIPTION_PLANS) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Explore All Membership Plans",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DeepTeal
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // GST Tax Invoices Section
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Receipt, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "GST Tax Invoices",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = DeepTeal
                            )
                        }
                        Text(
                            text = "${invoices.size} Invoices",
                            fontSize = 11.sp,
                            color = TextSecondaryMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (invoices.isEmpty()) {
                        Text(
                            text = "No invoices generated yet.",
                            fontSize = 12.sp,
                            color = TextSecondaryMuted
                        )
                    } else {
                        invoices.forEach { invoice ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SurfaceVariantWarm,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { viewModel.viewInvoice(invoice) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(invoice.invoiceNumber, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextPrimaryDark)
                                        Text("${invoice.issueDate} • ₹${invoice.totalAmount} (18% GST incl.)", fontSize = 11.sp, color = TextSecondaryMuted)
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.viewInvoice(invoice) },
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text("View Invoice", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Privacy & Account Controls
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Privacy & Discovery Controls",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = DeepTeal
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Hide Profile Toggle
                    val isHidden = user?.isProfileHidden ?: false
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Hide Profile from Discovery", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextPrimaryDark)
                            Text("Temporarily hide yourself from new daily curated circles", fontSize = 11.sp, color = TextSecondaryMuted)
                        }
                        Switch(
                            checked = isHidden,
                            onCheckedChange = { viewModel.setProfileVisibility(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = DeepTeal)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = Color(0xFFE2E9EA))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Pause Account
                    val isPaused = user?.isAccountPaused ?: false
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Pause Account", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextPrimaryDark)
                            Text("Take a break. Your existing matches and chats remain frozen safely.", fontSize = 11.sp, color = TextSecondaryMuted)
                        }
                        Switch(
                            checked = isPaused,
                            onCheckedChange = { viewModel.setAccountPaused(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = WarmCoral)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // DPDP Act 2023 Rights & Export
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "DPDP Act 2023 Data Protection",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = DeepTeal
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Under the Digital Personal Data Protection Act 2023, you have full ownership of your personal data.",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = TextSecondaryMuted
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.exportData { json ->
                                exportJsonContent = json
                                showExportDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Export My Data (JSON Bundle)")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { showDeleteConfirmDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = SafeErrorRed)
                    ) {
                        Icon(Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(16.dp), tint = SafeErrorRed)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete Account & Purge All Records")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Safety Center & Grievance Officer
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceVariantWarm),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
                    .clickable { viewModel.navigateTo(AppScreen.SAFETY_POLICIES) }
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Safety Center & Grievance Officer",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DeepTeal
                        )
                        Text(
                            text = "IT Rules 2021 contact • 24-hr resolution process • Takedown policy",
                            fontSize = 11.sp,
                            color = TextSecondaryMuted
                        )
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = DeepTeal)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Platform Admin Panel Access
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DeepTeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 540.dp)
                    .clickable { viewModel.navigateTo(AppScreen.ADMIN_PANEL) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = WarmCoral)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Platform Administrator Panel", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                        Text("Verification review, moderation queue, revenue & pricing config", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    // Export Data Dialog
    if (showExportDialog) {
        Dialog(onDismissRequest = { showExportDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("DPDP Act 2023 - Data Export", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepTeal)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Here is your full machine-readable personal data dump:", fontSize = 11.sp, color = TextSecondaryMuted)
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = SurfaceVariantWarm,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = exportJsonContent,
                            fontSize = 11.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            color = TextPrimaryDark,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { showExportDialog = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)
                    ) {
                        Text("Close Data Export")
                    }
                }
            }
        }
    }

    // Delete Account Dialog
    if (showDeleteConfirmDialog) {
        Dialog(onDismissRequest = { showDeleteConfirmDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Purge Account & All Data?", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SafeErrorRed)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This action will immediately erase your profile, selfie verifications, chats, and subscription records from our database in compliance with the DPDP Act 2023. This cannot be undone.",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = TextSecondaryMuted
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showDeleteConfirmDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                showDeleteConfirmDialog = false
                                viewModel.requestAccountPurge()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SafeErrorRed)
                        ) {
                            Text("Confirm Purge")
                        }
                    }
                }
            }
        }
    }
}
