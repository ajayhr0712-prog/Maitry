package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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
import com.example.ui.theme.WarmCoral
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.MaitryViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiscoveryTab(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val matches by viewModel.curatedMatches.collectAsState()
    val filterCity by viewModel.filterCity.collectAsState()
    val filterIntent by viewModel.filterIntent.collectAsState()

    var showFilters by remember { mutableStateOf(false) }

    // Curated daily batch filtering (exclude passed or already connected in main card stack)
    val filteredMatches = matches.filter { match ->
        (match.connectionStatus == "NONE" || match.connectionStatus == "SENT_HELLO") &&
                (filterCity == "All" || match.city.contains(filterCity, ignoreCase = true)) &&
                (filterIntent == "All" || match.intent.contains(filterIntent, ignoreCase = true))
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Daily Curation Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Daily Curated Circle",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepTeal
                        )
                        Text(
                            text = "Thoughtful profiles. No endless swiping.",
                            fontSize = 12.sp,
                            color = TextSecondaryMuted
                        )
                    }

                    FilterChip(
                        selected = showFilters,
                        onClick = { showFilters = !showFilters },
                        label = { Text("Filters", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.FilterList, contentDescription = null, modifier = Modifier.size(14.dp)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DeepTealContainer,
                            selectedLabelColor = DeepTeal
                        )
                    )
                }

                AnimatedVisibility(visible = showFilters) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(14.dp))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Filter by City:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimaryDark)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                val cities = listOf("All", "Mumbai", "Bengaluru", "Pune", "Delhi NCR", "Hyderabad", "Kolkata")
                                items(cities) { c ->
                                    FilterChip(
                                        selected = filterCity == c,
                                        onClick = { viewModel.filterCity.value = c },
                                        label = { Text(c, fontSize = 11.sp) }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Filter by Intent:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimaryDark)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                val intents = listOf("All", "Friendship", "Second Innings", "Relationship")
                                items(intents) { i ->
                                    FilterChip(
                                        selected = filterIntent == i,
                                        onClick = { viewModel.filterIntent.value = i },
                                        label = { Text(i, fontSize = 11.sp) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (filteredMatches.isEmpty()) {
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
                        Image(
                            painter = painterResource(id = R.drawable.img_chai_date),
                            contentDescription = "Chai",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "You're all caught up for today!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DeepTeal
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "To encourage thoughtful connections and prevent digital burnout, Maitry limits daily profiles. Check back tomorrow for your next curated circle.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = TextSecondaryMuted,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(filteredMatches, key = { it.id }) { match ->
                CuratedMatchCard(
                    match = match,
                    onSendHello = { viewModel.sendFriendlyHello(match) },
                    onPass = { viewModel.passMatch(match) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // padding for bottom nav
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CuratedMatchCard(
    match: CuratedMatchEntity,
    onSendHello: () -> Unit,
    onPass: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 540.dp)
            .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(24.dp))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header with Avatar and Basic Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarPlaceholder(
                    name = match.name,
                    size = 56,
                    backgroundColor = Color(match.accentColorHex)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${match.name}, ${match.age}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                        if (match.isVerified) {
                            Spacer(modifier = Modifier.width(6.dp))
                            VerifiedBadge()
                        }
                    }

                    Text(
                        text = match.profession,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondaryMuted
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = DeepTeal,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = match.city,
                            fontSize = 11.sp,
                            color = DeepTeal,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Intent Pill
            Surface(
                color = WarmCoralContainer,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolunteerActivism,
                        contentDescription = null,
                        tint = WarmCoral,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Looking for: ${match.intent}",
                        color = Color(0xFF6B1D0E),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bio
            Text(
                text = match.bio,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = TextPrimaryDark
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Interests Chips
            Text(
                text = "Interests & Passions",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DeepTeal
            )
            Spacer(modifier = Modifier.height(6.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                match.interests.split(",").forEach { interest ->
                    Surface(
                        color = SurfaceVariantWarm,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = interest.trim(),
                            fontSize = 11.sp,
                            color = TextPrimaryDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Languages
            Text(
                text = "Languages: ${match.languages}",
                fontSize = 11.sp,
                color = TextSecondaryMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            // "Why Maitry" Prompt Card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DeepTealContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Why I joined Maitry:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "\"${match.whyMaitryAnswer}\"",
                        fontSize = 12.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = TextPrimaryDark,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Icebreaker Prompt
            Surface(
                color = SurfaceVariantWarm,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.QuestionAnswer,
                        contentDescription = null,
                        tint = WarmCoral,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Ask me about:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmCoral
                        )
                        Text(
                            text = match.icebreakerQuestion,
                            fontSize = 12.sp,
                            color = TextPrimaryDark,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Divider(color = Color(0xFFE2E9EA))
            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onPass,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Pass")
                }

                Button(
                    onClick = onSendHello,
                    modifier = Modifier.weight(1.6f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmCoral)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Send Friendly Hello", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
