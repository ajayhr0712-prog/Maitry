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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.CuratedMatchEntity
import com.example.ui.components.AvatarPlaceholder
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
fun ConnectionsTab(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val matches by viewModel.curatedMatches.collectAsState()
    val mutualMatches = matches.filter { it.connectionStatus == "MUTUAL_MATCH" }

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
                    text = "Mutual Connections",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
                Text(
                    text = "Both members expressed interest. Chat is unlocked with scam protection.",
                    fontSize = 12.sp,
                    color = TextSecondaryMuted
                )
            }
        }

        if (mutualMatches.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 500.dp)
                        .padding(vertical = 40.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = DeepTealContainer,
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(30.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "No mutual matches yet",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DeepTeal
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Send a Friendly Hello in your Daily Curated Circle. When they accept, conversation will open here.",
                            fontSize = 12.sp,
                            color = TextSecondaryMuted,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(mutualMatches, key = { it.id }) { match ->
                ConnectionItemCard(
                    match = match,
                    onOpenChat = {
                        viewModel.openChat(match)
                        viewModel.selectTab(2) // Switch to chat tab
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun ConnectionItemCard(
    match: CuratedMatchEntity,
    onOpenChat: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 540.dp)
            .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(18.dp))
            .clickable { onOpenChat() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarPlaceholder(
                name = match.name,
                size = 52,
                backgroundColor = Color(match.accentColorHex)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${match.name}, ${match.age}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextPrimaryDark
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    VerifiedBadge()
                }

                Text(
                    text = match.profession,
                    fontSize = 12.sp,
                    color = TextSecondaryMuted
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = match.city, fontSize = 11.sp, color = DeepTeal, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    color = VerifiedGreenContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Mutual Connection • Safe Chat Unlocked",
                        color = VerifiedGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onOpenChat,
                colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                shape = RoundedCornerShape(16.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
