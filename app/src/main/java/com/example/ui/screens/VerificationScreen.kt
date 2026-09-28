package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.VerifiedGreenContainer
import com.example.ui.theme.WarmCoral
import com.example.viewmodel.MaitryViewModel

@Composable
fun VerificationScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val selfieCaptured by viewModel.selfieCaptured.collectAsState()
    val idUploaded by viewModel.idDocumentUploaded.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DeepTeal)
            }
            Text(
                text = "Identity Verification",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DeepTeal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = CircleShape,
            color = VerifiedGreenContainer,
            modifier = Modifier.size(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = VerifiedGreen,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Selfie Liveness & Verification",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = DeepTeal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "To preserve a safe, scam-free community, every member completes a quick selfie liveness verification before unlocking access.",
            fontSize = 13.sp,
            lineHeight = 19.sp,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 420.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Selfie Verification Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp)
                .border(
                    1.dp,
                    if (selfieCaptured) VerifiedGreen else Color(0xFFE2E9EA),
                    RoundedCornerShape(20.dp)
                )
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = if (selfieCaptured) VerifiedGreenContainer else DeepTealContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (selfieCaptured) Icons.Default.CheckCircle else Icons.Default.PhotoCamera,
                                    contentDescription = null,
                                    tint = if (selfieCaptured) VerifiedGreen else DeepTeal
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "1. Live Selfie Liveness",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimaryDark
                            )
                            Text(
                                text = if (selfieCaptured) "Photo matched & verified" else "Required for Verified Badge",
                                fontSize = 12.sp,
                                color = if (selfieCaptured) VerifiedGreen else TextSecondaryMuted
                            )
                        }
                    }

                    if (selfieCaptured) {
                        VerifiedBadge()
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (!selfieCaptured) {
                    Button(
                        onClick = { viewModel.selfieCaptured.value = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Capture Verification Selfie")
                    }
                } else {
                    Surface(
                        color = VerifiedGreenContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "✓ Biometric liveness check passed. Facial landmarks verified.",
                            color = VerifiedGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Optional Govt ID Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp)
                .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SurfaceVariantWarm,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.UploadFile, contentDescription = null, tint = DeepTeal)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "2. Optional Govt ID Upload",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimaryDark
                            )
                            Text(
                                text = if (idUploaded) "Masked ID securely reviewed" else "Aadhaar / Driving License / Passport",
                                fontSize = 12.sp,
                                color = TextSecondaryMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "🔒 Security guarantee: Stored encrypted at rest. Completely purged after age & name verification per DPDP Act 2023.",
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = TextSecondaryMuted
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { viewModel.idDocumentUploaded.value = !idUploaded },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (idUploaded) "✓ ID Uploaded (Purge Scheduled)" else "Upload Optional Govt ID")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.completeVerification() },
            enabled = selfieCaptured,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp)
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WarmCoral)
        ) {
            Text(
                text = "Continue to Paywall (Subscription Required)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Next step: Select your subscription plan. Free access is not available.",
            fontSize = 12.sp,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center
        )
    }
}
