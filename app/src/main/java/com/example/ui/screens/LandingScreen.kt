package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.theme.DeepTealDark
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.WarmCoral
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MaitryViewModel

@Composable
fun LandingScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. Top Brand Header ---
        item {
            Surface(
                color = CreamSurface,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                        Column {
                            Text(
                                text = "Maitry",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepTeal
                            )
                            Text(
                                text = "maitry.co.in",
                                fontSize = 11.sp,
                                color = TextSecondaryMuted
                            )
                        }
                    }

                    Row {
                        TextButton(onClick = { viewModel.navigateTo(AppScreen.HOW_IT_WORKS) }) {
                            Text("How It Works", color = DeepTeal, fontSize = 13.sp)
                        }
                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.AGE_GATE) },
                            colors = ButtonDefaults.buttonColors(containerColor = WarmCoral),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.height(38.dp)
                        ) {
                            Text("Join 18+", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // --- 2. Hero Section ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Branded Pill
                Surface(
                    color = WarmCoralContainer,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "🇮🇳 India's Subscription-Only Friendship & Dating Platform",
                        color = Color(0xFF8A2312),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Text(
                    text = "Friendship first,\nlove next.",
                    fontSize = 32.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepTealDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "A safe, respectful haven for intentional urban Indians (including 35+ & second-innings seekers). Zero free tier means zero fake profiles, zero unsolicited spam, and zero hookup culture.",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = TextSecondaryMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Hero Illustration
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_banner),
                        contentDescription = "Maitry genuine connections",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.navigateTo(AppScreen.AGE_GATE) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)
                ) {
                    Text(
                        text = "Enter Maitry (Strict 18+ Age Gate)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "No free tier • 100% verified • No auto-debit guarantee",
                    fontSize = 12.sp,
                    color = TextSecondaryMuted
                )
            }
        }

        // --- 3. The Subscription-Only Guarantee ---
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(24.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = DeepTealContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = DeepTeal)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Why 100% Subscription-Only?",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepTeal
                            )
                            Text(
                                text = "Our most important founding principle",
                                fontSize = 12.sp,
                                color = TextSecondaryMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    GuaranteePoint(
                        title = "Zero Free Lurkers & Fake Profiles",
                        desc = "Nobody can browse, like, match, or message without paying. This simple barrier instantly filters out catfishes, bots, and bored casual swipers."
                    )
                    GuaranteePoint(
                        title = "₹999 / Year (Just ₹83/month)",
                        desc = "Inclusive of 18% GST with immediate GST tax invoice. No hidden fees."
                    )
                    GuaranteePoint(
                        title = "One-Time Payment, NO Auto-Renewal",
                        desc = "We will NEVER silently charge your card or bank account. You decide when and if to renew."
                    )
                    GuaranteePoint(
                        title = "90-Day Match Preservation",
                        desc = "If your plan expires, we safely freeze your matches and profile for 90 days with renewal reminders at 15, 7, and 1 day."
                    )
                }
            }
        }

        // --- 4. How It Works (3 Steps) ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(20.dp)
            ) {
                Text(
                    text = "How Maitry Works",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
                Text(
                    text = "A calm, dignified alternative to endless swiping",
                    fontSize = 13.sp,
                    color = TextSecondaryMuted
                )

                Spacer(modifier = Modifier.height(16.dp))

                StepCard(
                    stepNumber = "1",
                    title = "Age Gate & Verified Onboarding",
                    description = "Must be 18+ to enter. We verify mobile OTP (+91) and require a selfie liveness check so you only talk to real, verified human beings."
                )
                StepCard(
                    stepNumber = "2",
                    title = "Choose Your Membership",
                    description = "Select the Annual (₹999/yr) or flexible plan. Payment is verified server-side with Razorpay, and a GST invoice is immediately emailed & available in your app."
                )
                StepCard(
                    stepNumber = "3",
                    title = "Daily Curated Matches & Safe Chat",
                    description = "Receive a curated batch of thoughtful profiles daily. Chat is text-only initially, with automatic scam filters. Photo exchange only unlocks after mutual opt-in."
                )
            }
        }

        // --- 5. Real Member Stories ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Voices from Maitry",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
                Spacer(modifier = Modifier.height(12.dp))

                TestimonialCard(
                    quote = "At 36, after coming out of a long marriage, the usual apps felt humiliating and predatory. Maitry's subscription barrier and focus on starting as friends gave me back my dignity.",
                    author = "Rituja M., 36",
                    city = "Pune"
                )
                TestimonialCard(
                    quote = "Because everyone paid ₹999, people actually reply with full, thoughtful sentences! We talked about literature and Hindustani classical for two weeks before meeting for chai.",
                    author = "Vikram S., 30",
                    city = "Bengaluru"
                )
            }
        }

        // --- 6. Safety & Legal Compliance Badge ---
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceVariantWarm),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_safe_trust),
                        contentDescription = "Safe & Trust",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Compliant with Indian Law",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepTeal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "• DPDP Act 2023: Purpose limitation & right to delete\n• IT Rules 2021: Designated Grievance Officer\n• 24-Hour Moderation Queue & Scam Shield",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = TextPrimaryDark
                        )
                    }
                }
            }
        }

        // --- 7. FAQ Accordion ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Frequently Asked Questions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
                Spacer(modifier = Modifier.height(10.dp))

                FaqItem(
                    question = "Why is there absolutely no free tier?",
                    answer = "Free dating platforms suffer from catfishes, bots, unsolicited solicitations, and ghosting. By making Maitry subscription-only, every member is committed to respectful, genuine conversation."
                )
                FaqItem(
                    question = "Will I be auto-charged when my year ends?",
                    answer = "Never. We enforce a strict NO auto-renewal policy. We send gentle reminders at 15, 7, and 1 day before expiry, and you explicitly choose if you want to renew."
                )
                FaqItem(
                    question = "How is my selfie and government ID stored?",
                    answer = "Under DPDP Act 2023 regulations, verification images are encrypted in transit and at rest. ID documents are used solely for age/identity confirmation and purged post-verification."
                )
            }
        }

        // --- 8. Footer & Legal Disclosures ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DeepTealDark)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Maitry (maitry.co.in)",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Friendship first, love next.",
                    color = WarmCoral,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.HOW_IT_WORKS) }) {
                        Text("How It Works", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.SAFETY_POLICIES) }) {
                        Text("Safety & Grievance", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.LEGAL_DOCS) }) {
                        Text("Draft Terms & Policies", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Grievance Officer: Adv. Ramesh V. Kulkarni\ngrievance@maitry.co.in • 402 Barakhamba Rd, Connaught Place, New Delhi - 110001",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "© 2026 Maitry Digital Trust. All legal policies are draft placeholders for review.",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun GuaranteePoint(title: String, desc: String) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = VerifiedGreen,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryDark
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = TextSecondaryMuted
            )
        }
    }
}

@Composable
private fun StepCard(stepNumber: String, title: String, description: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(1.dp, Color(0xFFE7ECEE), RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = CircleShape,
                color = DeepTeal,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = stepNumber, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DeepTeal)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, fontSize = 12.sp, lineHeight = 18.sp, color = TextSecondaryMuted)
            }
        }
    }
}

@Composable
private fun TestimonialCard(quote: String, author: String, city: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\"$quote\"",
                fontSize = 13.sp,
                lineHeight = 20.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = TextPrimaryDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = author,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
                Text(
                    text = " • $city",
                    fontSize = 12.sp,
                    color = TextSecondaryMuted
                )
            }
        }
    }
}

@Composable
private fun FaqItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = DeepTeal
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = answer,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = TextSecondaryMuted
                    )
                }
            }
        }
    }
}
