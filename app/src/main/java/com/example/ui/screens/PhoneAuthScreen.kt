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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarmCoral
import com.example.viewmodel.MaitryViewModel

@Composable
fun PhoneAuthScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val phone by viewModel.inputPhone.collectAsState()
    val otp by viewModel.inputOtp.collectAsState()
    val isOtpSent by viewModel.isOtpSent.collectAsState()
    val generatedOtp by viewModel.generatedOtp.collectAsState()

    var localPhone by remember { mutableStateOf(phone.ifEmpty { "9820123456" }) }
    var localOtp by remember { mutableStateOf(otp) }

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
                text = "Phone Verification",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DeepTeal
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            shape = CircleShape,
            color = DeepTealContainer,
            modifier = Modifier.size(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Enter Indian Mobile Number",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = DeepTeal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "We will send a 6-digit one-time password to verify your identity. Your phone number is never shared with other users.",
            fontSize = 13.sp,
            lineHeight = 19.sp,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 420.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp)
                .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Mobile Number (+91)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        color = SurfaceVariantWarm,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .padding(end = 8.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        ) {
                            Text(
                                text = "🇮🇳 +91",
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark,
                                fontSize = 15.sp
                            )
                        }
                    }

                    OutlinedTextField(
                        value = localPhone,
                        onValueChange = {
                            if (it.length <= 10) {
                                localPhone = it
                                viewModel.inputPhone.value = it
                            }
                        },
                        placeholder = { Text("10-digit number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        viewModel.inputPhone.value = localPhone
                        viewModel.sendOtp()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)
                ) {
                    Text(if (isOtpSent) "Resend OTP" else "Send 6-Digit OTP")
                }

                if (isOtpSent) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Enter 6-Digit Code",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryDark
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = localOtp,
                        onValueChange = {
                            if (it.length <= 6) {
                                localOtp = it
                                viewModel.inputOtp.value = it
                            }
                        },
                        placeholder = { Text("e.g. $generatedOtp") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = SurfaceVariantWarm,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                localOtp = generatedOtp
                                viewModel.inputOtp.value = generatedOtp
                            }
                    ) {
                        Text(
                            text = "💡 Tap to auto-fill SMS code: $generatedOtp",
                            fontSize = 12.sp,
                            color = DeepTeal,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.inputOtp.value = localOtp
                            viewModel.verifyOtp()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WarmCoral)
                    ) {
                        Text("Verify & Continue", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.widthIn(max = 380.dp)
        ) {
            Icon(Icons.Default.Security, contentDescription = null, tint = TextSecondaryMuted, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Protected by rate limiting and encrypted session management. Session tokens rotate upon login.",
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = TextSecondaryMuted
            )
        }
    }
}
