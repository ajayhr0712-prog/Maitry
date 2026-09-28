package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.WarmCoral
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel

@Composable
fun HowItWorksScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
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
                    text = "How Maitry Works",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "The Friendship-First Philosophy",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Most apps push you into instant high-stakes romance, leading to shallow swiping and ghosting. Maitry is intentionally structured so you start as companions, share thoughts over chai, and allow affection to mature naturally.",
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = TextPrimaryDark
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        item {
            DetailStep(
                number = "1",
                title = "Strict Age Gate & Phone OTP (+91)",
                desc = "We enforce an 18+ age verification. Every user authenticates their Indian mobile number via secure SMS OTP. No burner social logins or anonymous accounts.",
                icon = Icons.Default.Shield
            )
            DetailStep(
                number = "2",
                title = "Selfie Liveness Verification",
                desc = "A real-time selfie liveness match confirms you are the person in your photos. Verified members receive the green shield badge.",
                icon = Icons.Default.VerifiedUser
            )
            DetailStep(
                number = "3",
                title = "The Paywall (Zero Free Tier)",
                desc = "To guarantee high intent and eradicate trolls, catfishes, and spam bots, everyone pays for membership (₹999/year inclusive of 18% GST). No free browsing or chatting exists.",
                icon = Icons.Default.Lock
            )
            DetailStep(
                number = "4",
                title = "Daily Curated Circle",
                desc = "Instead of an endless addiction machine, Maitry provides a focused batch of vetted profiles each day based on mutual intellectual and emotional compatibility.",
                icon = Icons.Default.People
            )
            DetailStep(
                number = "5",
                title = "Safe Text-First Chat",
                desc = "Once mutual interest is established, conversation unlocks. Early messages strictly block phone numbers and external links. Photos can only be sent once both members mutually opt in.",
                icon = Icons.Default.Chat
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Button(
                onClick = { viewModel.navigateTo(AppScreen.AGE_GATE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarmCoral)
            ) {
                Text("Get Started on Maitry", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun DetailStep(
    number: String,
    title: String,
    desc: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp)
            .padding(vertical = 5.dp)
            .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = CircleShape,
                color = DeepTeal,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(number, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepTeal)
                Spacer(modifier = Modifier.height(4.dp))
                Text(desc, fontSize = 12.sp, lineHeight = 18.sp, color = TextSecondaryMuted)
            }
        }
    }
}
