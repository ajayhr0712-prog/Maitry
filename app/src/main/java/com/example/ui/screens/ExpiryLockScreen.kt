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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel

@Composable
fun ExpiryLockScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = WarmCoralContainer,
            modifier = Modifier.size(72.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = WarmCoral,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Membership Expired",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DeepTeal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Maitry is strictly subscription-only. Your active period has concluded. Because we never auto-debit your card, we require your explicit renewal to continue.",
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 420.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 90-day preservation card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(18.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.HourglassBottom,
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "90-Day Match Preservation Guarantee",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DeepTeal
                    )
                    Text(
                        text = "Your profile, connection history, and chats are safely frozen for 90 days. You can renew at any time to resume uninterrupted conversations.",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = TextSecondaryMuted
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.navigateTo(AppScreen.PAYWALL) },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WarmCoral)
        ) {
            Text("Renew Membership (Plans from ₹199)", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { viewModel.navigateTo(AppScreen.LANDING) },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Back to Welcome Screen")
        }
    }
}
