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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.CuratedMatchEntity
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
import com.example.viewmodel.MaitryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatDetailScreen(
    match: CuratedMatchEntity,
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    var messageText by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var showBlockDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top App Bar
        Surface(
            color = CreamSurface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.closeChat() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DeepTeal)
                }

                AvatarPlaceholder(
                    name = match.name,
                    size = 40,
                    backgroundColor = Color(match.accentColorHex)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${match.name}, ${match.age}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimaryDark
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        VerifiedBadge()
                    }
                    Text(
                        text = "${match.profession} • ${match.city}",
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )
                }

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = DeepTeal)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Report Member", color = SafeErrorRed) },
                            leadingIcon = { Icon(Icons.Default.Flag, contentDescription = null, tint = SafeErrorRed) },
                            onClick = {
                                showMenu = false
                                showReportDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Block & Remove") },
                            leadingIcon = { Icon(Icons.Default.Block, contentDescription = null, tint = TextSecondaryMuted) },
                            onClick = {
                                showMenu = false
                                showBlockDialog = true
                            }
                        )
                    }
                }
            }
        }

        // Safety Shield Banner
        Surface(
            color = WarmCoralContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = WarmCoral,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Safe Chat Shield: Phone numbers, external links & UPI/money requests are blocked. Keep conversations inside Maitry.",
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = Color(0xFF6B1D0E)
                )
            }
        }

        // Mutual Photo Exchange Lock Banner
        Surface(
            color = DeepTealContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = null,
                        tint = DeepTeal,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (match.photoOptInMe) "You opted in to photo exchange. Waiting for mutual consent." else "Photo sharing locked. Mutual consent required.",
                        fontSize = 11.sp,
                        color = DeepTeal,
                        fontWeight = FontWeight.Medium
                    )
                }

                TextButton(
                    onClick = { viewModel.togglePhotoSharing(match.id, !match.photoOptInMe) }
                ) {
                    Text(
                        text = if (match.photoOptInMe) "Opt Out" else "Opt In",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepTeal
                    )
                }
            }
        }

        // Message List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            reverseLayout = false
        ) {
            items(messages, key = { it.id }) { msg ->
                MessageBubble(msg = msg)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Chat Input Row
        Surface(
            color = CreamSurface,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Write a safe, respectful message...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = CircleShape,
                    color = if (messageText.isNotBlank()) WarmCoral else Color(0xFFD0D7D9),
                    modifier = Modifier.size(46.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                viewModel.sendChatMessage(match.id, messageText)
                                messageText = ""
                            }
                        },
                        enabled = messageText.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    // Report Dialog
    if (showReportDialog) {
        ReportUserDialog(
            matchName = match.name,
            onDismiss = { showReportDialog = false },
            onSubmit = { reason, details ->
                viewModel.submitReport(match.id, match.name, reason, details)
                showReportDialog = false
            }
        )
    }

    // Block Dialog
    if (showBlockDialog) {
        Dialog(onDismissRequest = { showBlockDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Block ${match.name}?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "They will be removed from your connections, and cannot see your profile or send you messages.",
                        fontSize = 12.sp,
                        color = TextSecondaryMuted
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showBlockDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                viewModel.blockUser(match.id, match.name)
                                showBlockDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SafeErrorRed)
                        ) {
                            Text("Block Member")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(msg: ChatMessageEntity) {
    val isMe = msg.sender == "ME"
    val timeFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    val timeStr = timeFormat.format(Date(msg.timestamp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isMe) 16.dp else 2.dp,
                bottomEnd = if (isMe) 2.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isMe) DeepTeal else CreamSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    text = msg.text,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = if (isMe) Color.White else TextPrimaryDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timeStr,
                    fontSize = 10.sp,
                    color = if (isMe) Color.White.copy(alpha = 0.7f) else TextSecondaryMuted,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun ReportUserDialog(
    matchName: String,
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    val reasons = listOf(
        "Financial scam / Money or UPI request",
        "Sextortion or blackmail attempt",
        "Commercial escort / solicitation",
        "Abusive, vulgar, or harassing messages",
        "Fake profile or impersonation"
    )
    var selectedReason by remember { mutableStateOf(reasons[0]) }
    var details by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Flag, contentDescription = null, tint = SafeErrorRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Report $matchName",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = SafeErrorRed
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reports are sent directly to the Maitry 24-Hour Moderation Queue in compliance with IT Rules 2021.",
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = TextSecondaryMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                reasons.forEach { r ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedReason == r,
                            onClick = { selectedReason = r },
                            colors = RadioButtonDefaults.colors(selectedColor = DeepTeal)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = r, fontSize = 11.sp, color = TextPrimaryDark)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    placeholder = { Text("Additional details (optional)...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onSubmit(selectedReason, details) },
                        colors = ButtonDefaults.buttonColors(containerColor = SafeErrorRed)
                    ) {
                        Text("Submit Report & Block")
                    }
                }
            }
        }
    }
}
