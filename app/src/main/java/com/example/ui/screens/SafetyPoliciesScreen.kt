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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.SafeErrorContainer
import com.example.ui.theme.SafeErrorRed
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarmCoral
import com.example.viewmodel.MaitryViewModel

@Composable
fun SafetyPoliciesScreen(
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
                    text = "Safety & Grievance Redressal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
            }
        }

        // Statutory Grievance Redressal Card (IT Rules 2021)
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = DeepTealContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Gavel, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Designated Grievance Officer",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = DeepTeal
                            )
                            Text(
                                text = "Under Information Technology (Intermediary Guidelines) Rules, 2021",
                                fontSize = 11.sp,
                                color = TextSecondaryMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Name: Adv. Ramesh V. Kulkarni\nDesignation: Senior Legal Counsel & Grievance Officer\nEntity: Maitry Digital Trust Ltd (maitry.co.in)\nAddress: 402 Barakhamba Road, Connaught Place, New Delhi - 110001, India\nEmail: grievance@maitry.co.in",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = TextPrimaryDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = SurfaceVariantWarm,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "⏱️ SLA Guarantee: All grievances acknowledged within 24 hours and fully investigated & resolved within 15 days of receipt.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = DeepTeal,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // DPDP Act 2023 Principles
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Policy, contentDescription = null, tint = DeepTeal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "DPDP Act 2023 Data Protection Commitments",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DeepTeal
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. Purpose Limitation: Profile data is exclusively used to present match candidates and enforce anti-harassment safeguards.\n" +
                                "2. Right to Correction & Erasure: You may request complete erasure of your account and verified identifiers at any time via the Account tab.\n" +
                                "3. Data Breach Incident Protocol: Any unauthorized access is reported to the Data Protection Board of India and affected data principals without undue delay.\n" +
                                "4. No Secondary Sharing: Maitry never sells, leases, or provides user profile databases to advertisers or commercial brokers.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = TextPrimaryDark
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Anti-Scam & Extortion Warnings
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SafeErrorContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ReportProblem, contentDescription = null, tint = SafeErrorRed)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Zero Tolerance for Scams & Extortion",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = SafeErrorRed
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Never send money or UPI payments to someone you have met online, regardless of their emergency story.\n" +
                                "• Never share compromising photos. Sextortion and blackmail are cognizable criminal offenses under the IT Act 2000 and Indian Penal Code / BNS.\n" +
                                "• Commercial solicitation, escort advertisements, and unauthorized financial promotions lead to immediate permanent ban and submission to authorities.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = Color(0xFF6B1414)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
