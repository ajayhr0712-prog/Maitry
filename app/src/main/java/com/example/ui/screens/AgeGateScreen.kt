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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Shield
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
import com.example.ui.theme.SafeErrorContainer
import com.example.ui.theme.SafeErrorRed
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.VerifiedGreenContainer
import com.example.ui.theme.WarmCoral
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel

@Composable
fun AgeGateScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val birthYear by viewModel.birthYear.collectAsState()
    val birthMonth by viewModel.birthMonth.collectAsState()
    val birthDay by viewModel.birthDay.collectAsState()
    val ageErrorMessage by viewModel.ageErrorMessage.collectAsState()
    val ageValidationPassed by viewModel.ageValidationPassed.collectAsState()
    val inputAge by viewModel.inputAge.collectAsState()

    var dayText by remember { mutableStateOf(birthDay.toString()) }
    var monthText by remember { mutableStateOf(birthMonth.toString()) }
    var yearText by remember { mutableStateOf(birthYear.toString()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Back Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DeepTeal)
            }
            Text(
                text = "Age Verification",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DeepTeal
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            shape = CircleShape,
            color = DeepTeal,
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = WarmCoral,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Strict 18+ Age Gate",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DeepTeal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Maitry is exclusively for adults aged 18 and older. Please enter your authentic date of birth to continue.",
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 420.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Date Inputs Box
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
                    text = "Date of Birth (DD / MM / YYYY)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = dayText,
                        onValueChange = {
                            if (it.length <= 2) {
                                dayText = it
                                it.toIntOrNull()?.let { d -> if (d in 1..31) viewModel.birthDay.value = d }
                            }
                        },
                        label = { Text("Day") },
                        placeholder = { Text("DD") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = monthText,
                        onValueChange = {
                            if (it.length <= 2) {
                                monthText = it
                                it.toIntOrNull()?.let { m -> if (m in 1..12) viewModel.birthMonth.value = m }
                            }
                        },
                        label = { Text("Month") },
                        placeholder = { Text("MM") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = yearText,
                        onValueChange = {
                            if (it.length <= 4) {
                                yearText = it
                                it.toIntOrNull()?.let { y -> if (y in 1920..2026) viewModel.birthYear.value = y }
                            }
                        },
                        label = { Text("Year") },
                        placeholder = { Text("YYYY") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1.3f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (ageErrorMessage != null) {
                    Surface(
                        color = SafeErrorContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Block, contentDescription = null, tint = SafeErrorRed)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = ageErrorMessage ?: "",
                                color = SafeErrorRed,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                } else if (ageValidationPassed) {
                    Surface(
                        color = VerifiedGreenContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VerifiedGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Age confirmed: $inputAge years old. You are eligible to join Maitry.",
                                color = VerifiedGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val ok = viewModel.validateAge()
                if (ok) {
                    viewModel.navigateTo(AppScreen.PHONE_AUTH)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp)
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)
        ) {
            Text("Confirm & Continue to Phone OTP", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enforced in accordance with Indian IT Rules & Protection of Children policies. Minors are strictly prohibited.",
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 400.dp)
        )
    }
}
