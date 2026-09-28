package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import com.example.ui.components.SubscriptionStatusBadge
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.WarmCoral
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel

@Composable
fun MainAppScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val activeChatMatch by viewModel.activeChatMatch.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    val matches by viewModel.curatedMatches.collectAsState()

    val mutualCount = matches.count { it.connectionStatus == "MUTUAL_MATCH" }

    BackHandler {
        val handled = viewModel.navigateBack()
        if (!handled && selectedTab != 0) {
            viewModel.selectTab(0)
        }
    }

    if (activeChatMatch != null) {
        ChatDetailScreen(
            match = activeChatMatch!!,
            viewModel = viewModel
        )
    } else {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(CreamBackground),
            topBar = {
                Surface(
                    color = CreamSurface,
                    shadowElevation = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = DeepTeal,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = WarmCoral,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Maitry",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepTeal
                            )
                            Text(
                                text = "Friendship first, love next",
                                fontSize = 11.sp,
                                color = TextSecondaryMuted
                            )
                        }

                        // Subscription Badge
                        SubscriptionStatusBadge(
                            isSubscribed = user?.isSubscribed ?: true,
                            planName = user?.planName ?: "Annual Member"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.ADMIN_PANEL) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = "Admin Panel",
                                tint = DeepTeal,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = CreamSurface,
                    contentColor = DeepTeal,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { viewModel.selectTab(0) },
                        icon = { Icon(Icons.Default.VolunteerActivism, contentDescription = "Daily Circle") },
                        label = { Text("Daily Circle", fontSize = 11.sp, fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepTeal,
                            selectedTextColor = DeepTeal,
                            indicatorColor = DeepTealContainer
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { viewModel.selectTab(1) },
                        icon = {
                            if (mutualCount > 0) {
                                BadgedBox(badge = { Badge { Text("$mutualCount") } }) {
                                    Icon(Icons.Default.People, contentDescription = "Mutuals")
                                }
                            } else {
                                Icon(Icons.Default.People, contentDescription = "Mutuals")
                            }
                        },
                        label = { Text("Mutuals", fontSize = 11.sp, fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepTeal,
                            selectedTextColor = DeepTeal,
                            indicatorColor = DeepTealContainer
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { viewModel.selectTab(2) },
                        icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Safe Chat") },
                        label = { Text("Safe Chat", fontSize = 11.sp, fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepTeal,
                            selectedTextColor = DeepTeal,
                            indicatorColor = DeepTealContainer
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { viewModel.selectTab(3) },
                        icon = { Icon(Icons.Default.Shield, contentDescription = "Account & Safety") },
                        label = { Text("My Safety", fontSize = 11.sp, fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepTeal,
                            selectedTextColor = DeepTeal,
                            indicatorColor = DeepTealContainer
                        )
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    0 -> DiscoveryTab(viewModel = viewModel)
                    1 -> ConnectionsTab(viewModel = viewModel)
                    2 -> ConnectionsTab(viewModel = viewModel) // Directs to connections to choose chat
                    3 -> AccountSafetyTab(viewModel = viewModel)
                }
            }
        }
    }
}
