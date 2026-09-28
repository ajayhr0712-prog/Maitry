package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.WarmCoral
import com.example.viewmodel.MaitryViewModel

@Composable
fun LegalDocsScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf(
        "Terms of Use",
        "Privacy (DPDP Act)",
        "Refund & Billing",
        "Community Guidelines",
        "Grievance Redressal"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // App Bar
        Surface(
            color = CreamSurface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DeepTeal)
                }
                Column {
                    Text(
                        text = "Mandatory Legal Policies",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepTeal
                    )
                    Text(
                        text = "Compliant with IT Act 2000, IT Rules 2021 & DPDP Act 2023",
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )
                }
            }
        }

        // Statutory Notice Bar
        Surface(
            color = GoldContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Gavel, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Published by Maitry Digital Trust Ltd (maitry.co.in). Review legally binding terms below.",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6B4500)
                )
            }
        }

        // Tab Row
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 12.dp,
            containerColor = CreamSurface,
            contentColor = DeepTeal
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 680.dp)
                        .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        when (selectedTab) {
                            0 -> ComprehensiveTermsOfUse()
                            1 -> ComprehensivePrivacyPolicy()
                            2 -> ComprehensiveRefundPolicy()
                            3 -> ComprehensiveCommunityGuidelines()
                            4 -> ComprehensiveGrievanceRedressal()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComprehensiveTermsOfUse() {
    PolicyHeader("TERMS OF USE & SERVICE AGREEMENT", "Last updated: September 2026 • Valid for maitry.co.in")

    PolicySection(
        title = "1. Acceptance of Terms & Legal Entity",
        content = "This document is an electronic record under the Information Technology Act, 2000 and rules made thereunder. By creating an account or accessing the Maitry platform ('Platform', available at maitry.co.in and associated mobile applications), operated by Maitry Digital Trust Ltd ('Company', 'we', 'us', or 'our'), you unequivocally agree to be bound by these Terms of Use."
    )

    PolicySection(
        title = "2. Strict Age Gate (18+ Requirement)",
        content = "Eligibility: You must be at least 18 (eighteen) years of age to access or utilize any service on Maitry. Underage usage is strictly prohibited. By registering, you warrant that you are an adult under Indian law possessing full capacity to contract. Any account created with false date of birth or by individuals below 18 will be permanently terminated without refund, and relevant data reported to authorities if requested."
    )

    PolicySection(
        title = "3. Mandatory Subscription-Only Model (No Free Tier)",
        content = "Maitry operates strictly on an upfront paid subscription model. To eradicate spam bots, commercial solicitations, harassment, and catfishing, there is NO free browsing, profile liking, matching, or messaging tier. You must hold an active, paid membership to interact with other data principals on the platform."
    )

    PolicySection(
        title = "4. User Registration, Authentication & Verification",
        content = "• Mobile Authentication: Registration requires a valid Indian mobile number (+91) verified via one-time SMS password (OTP).\n• Liveness Verification: To protect members, users must submit a real-time biometric selfie liveness capture to confirm identity match.\n• Identity Integrity: Impersonation, use of stolen photographs, or deceptive profiles constitute fraud under Section 66D of the IT Act and will result in criminal reporting."
    )

    PolicySection(
        title = "5. Prohibited Conduct & Zero-Tolerance Violations",
        content = "You expressly covenant and agree that you shall NOT:\n" +
                "a) Solicit, demand, or exchange money, loans, UPI transfers, cryptocurrency, or financial investments under any circumstance.\n" +
                "b) Engage in commercial sex work, escort services, prostitution, or adult entertainment solicitation.\n" +
                "c) Share nude, sexually explicit, pornographic, or obscene imagery.\n" +
                "d) Engage in extortion, blackmail, or cyber-stalking (cognizable offenses under IT Act Section 66E/67 and Bharatiya Nyaya Sanhita).\n" +
                "e) Bypass or attempt to circumvent automated safety scanners that monitor early chats for phone numbers, external messaging links, or malicious URLs."
    )

    PolicySection(
        title = "6. Safe Chat Protocol & Automated Filtering",
        content = "To safeguard users from off-platform fraud and sextortion, Maitry employs automated message filtering that restricts the transmission of 10-digit mobile numbers and external links in initial messages. Photo sharing is locked by default and activates solely after both parties provide explicit mutual opt-in."
    )

    PolicySection(
        title = "7. Termination, Suspension & 24-Hour Moderation Action",
        content = "The Company reserves the unilateral right to suspend or permanently ban any account flagged by members or identified by moderation algorithms as violating safety charters, without notice or refund. Reports are processed by our 24-hour moderation queue."
    )

    PolicySection(
        title = "8. Governing Law & Dispute Resolution",
        content = "These Terms shall be construed, interpreted, and governed exclusively in accordance with the substantive laws of India. Any disputes arising out of or in connection with the Platform shall be subject to the exclusive jurisdiction of the competent courts located in New Delhi, India."
    )
}

@Composable
private fun ComprehensivePrivacyPolicy() {
    PolicyHeader("PRIVACY POLICY", "Compliant with Digital Personal Data Protection (DPDP) Act, 2023")

    PolicySection(
        title = "1. Identity of Data Fiduciary",
        content = "Maitry Digital Trust Ltd, with registered office at 402 Barakhamba Road, Connaught Place, New Delhi - 110001, acts as the 'Data Fiduciary' responsible for determining the purpose and means of processing personal data on maitry.co.in."
    )

    PolicySection(
        title = "2. Specific Categories of Personal Data Collected",
        content = "We collect only data necessary for verified, safe matchmaking:\n" +
                "• Primary Identifiers: Indian mobile phone number (+91), verified via OTP.\n" +
                "• Profile Characteristics: Display name, age, birthdate, gender, city, intent (e.g. Friendship first), biography, interests, and spoken languages.\n" +
                "• Biometric Verification Data: Real-time selfie images captured solely to verify facial similarity and liveness.\n" +
                "• Optional Identification Documents: Government-issued IDs submitted optionally are encrypted, verified, and purged post-verification.\n" +
                "• Transactional Records: Payment confirmation IDs from Razorpay, invoice records, and GST billing address. Note: Full card numbers and UPI PINs are never collected or stored by Maitry."
    )

    PolicySection(
        title = "3. Purpose Limitation (Section 4 & 5, DPDP Act 2023)",
        content = "Personal data is processed strictly for specified, lawful purposes:\n" +
                "a) Authenticating user identity and enforcing the 18+ legal age limit.\n" +
                "b) Generating curated daily circles of compatible verified members.\n" +
                "c) Preventing cyber-fraud, harassment, and off-platform exploitation.\n" +
                "d) Issuing GST-compliant tax invoices as required under Indian taxation law.\n" +
                "Maitry does NOT sell, rent, or trade your personal data to data brokers or third-party marketing networks."
    )

    PolicySection(
        title = "4. Strict 90-Day Retention Schedule & Automated Purge",
        content = "• Active Period: Data is maintained throughout your active paid membership.\n• Expiry Retention: When your subscription concludes, profile records and matches are frozen for exactly 90 (ninety) days to allow seamless renewal.\n• Irreversible Purge: If renewal does not occur within 90 days, or upon your explicit request for account erasure, all profile data, chat logs, and verification assets are irreversibly deleted from our primary databases."
    )

    PolicySection(
        title = "5. Data Principal Rights under Indian Law",
        content = "As a Data Principal under the DPDP Act 2023, you hold the following statutory rights:\n" +
                "• Right to Access: Request a readable summary of personal data held about you.\n" +
                "• Right to Data Export: Download your machine-readable JSON data bundle via the in-app privacy center.\n" +
                "• Right to Erasure: Trigger instant account purge and deletion via the Account tab.\n" +
                "• Right to Grievance Redressal: Contact our designated Grievance Officer regarding any data processing concern."
    )

    PolicySection(
        title = "6. Security Safeguards & Encryption Standards",
        content = "All personal data is encrypted in transit using Transport Layer Security (TLS 1.3) and at rest utilizing AES-256 cryptographic standards. Database storage is hosted in compliant data centers located within the Republic of India."
    )
}

@Composable
private fun ComprehensiveRefundPolicy() {
    PolicyHeader("REFUND, CANCELLATION & BILLING POLICY", "Consumer Protection (E-Commerce) Rules, 2020 Compliant")

    PolicySection(
        title = "1. Upfront Pricing Transparency & GST Details",
        content = "Maitry guarantees zero hidden charges. All subscription plans clearly itemize the base service charge plus 18% Goods & Services Tax (GST) under Services Accounting Code (SAC) 998431 prior to payment confirmation:\n" +
                "• Annual Plan: ₹999 total (Base ₹846.61 + 18% GST ₹152.39)\n" +
                "• 6-Month Plan: ₹599 total (Base ₹507.63 + 18% GST ₹91.37)\n" +
                "• 1-Month Plan: ₹199 total (Base ₹168.64 + 18% GST ₹30.36)"
    )

    PolicySection(
        title = "2. Strict NO Auto-Renewal Guarantee",
        content = "To protect consumers against involuntary charges, Maitry operates on a strictly NON-RECURRING billing architecture. We do NOT maintain recurring UPI auto-debit mandates or automatic credit card rebills. Subscriptions expire automatically unless you actively initiate renewal."
    )

    PolicySection(
        title = "3. 7-Day Fair Window Refund Policy",
        content = "We offer a 7-day fair refund guarantee under the following conditions:\n" +
                "• Eligibility: You may request a full refund within 7 (seven) days of initial payment if you have not initiated any chat conversations or expressed mutual interest, and experienced demonstrable technical failures.\n" +
                "• Exclusions: Accounts suspended or permanently banned for violating Community Guidelines, anti-scam rules, or harassment policies are strictly non-refundable.\n" +
                "• Refund Method: Approved refunds are credited back to the original source instrument (bank account/UPI/card) within 5-7 business days via Razorpay."
    )

    PolicySection(
        title = "4. Tax Invoice Delivery & Records",
        content = "Every completed transaction instantly generates a GST Tax Invoice featuring the Company's GSTIN, SAC Code, invoice serial number, and CGST/SGST/IGST breakdown. Invoices remain accessible at all times in your app dashboard."
    )
}

@Composable
private fun ComprehensiveCommunityGuidelines() {
    PolicyHeader("COMMUNITY GUIDELINES & ANTI-SEXTORTION CHARTER", "Zero tolerance for harassment, scams, and commercial solicitation")

    PolicySection(
        title = "1. The Friendship-First Ethos",
        content = "Maitry is built for urban Indians seeking dignified, intentional connections that begin as friends. We value emotional maturity, slow conversation, and mutual respect. Treat every member with courteous regard."
    )

    PolicySection(
        title = "2. Absolute Zero Tolerance for Financial Fraud",
        content = "• Never solicit money: Asking a connection for money, emergency medical assistance, loan transfers, or investment participation is strictly illegal on Maitry.\n• Blacklisting: Sharing UPI IDs, QR codes, or bank details triggers an instant permanent ban, telephone blacklisting, and reporting to the National Cyber Crime Reporting Portal (cybercrime.gov.in)."
    )

    PolicySection(
        title = "3. Anti-Sextortion & Non-Consensual Imagery Protection",
        content = "• Blackmail & Sextortion: Demanding money or favors under threat of exposing photos or personal chats is punishable under Section 66E of the IT Act and Section 384/354A of BNS/IPC.\n• Zero Nudity: Sending unsolicited explicit images or asking for sexually explicit material is grounds for immediate termination.\n• Mutual Photo Lock: Photos can never be shared in chat until both parties have explicitly toggled opt-in consent."
    )

    PolicySection(
        title = "4. Offline Meeting Safety Protocol",
        content = "When meeting a connection in person:\n" +
                "• Choose busy, well-lit public places (e.g. established cafes, tea houses, heritage centers).\n" +
                "• Arrange your own independent transportation.\n" +
                "• Inform a family member or trusted friend about your plans and venue.\n" +
                "• Keep personal finances and private residences private until mutual trust is solidly grounded."
    )
}

@Composable
private fun ComprehensiveGrievanceRedressal() {
    PolicyHeader("STATUTORY GRIEVANCE REDRESSAL MECHANISM", "Rule 3(2) of Information Technology (Intermediary Guidelines) Rules, 2021")

    PolicySection(
        title = "1. Designated Grievance Officer Contact Details",
        content = "In accordance with Information Technology Rules, 2021, the contact details of the statutory Grievance Officer for Maitry are:\n\n" +
                "• Officer Name: Adv. Ramesh V. Kulkarni\n" +
                "• Official Designation: Senior Grievance Redressal Officer\n" +
                "• Company: Maitry Digital Trust Ltd (maitry.co.in)\n" +
                "• Physical Address: 402 Barakhamba Road, Connaught Place, New Delhi - 110001, India\n" +
                "• Grievance Desk Email: grievance@maitry.co.in\n" +
                "• Direct Telephone: +91 11 4982 3000 (Mon - Fri, 10:00 AM - 6:00 PM IST)"
    )

    PolicySection(
        title = "2. Statutory Timelines & SLA Guarantee",
        content = "• Acknowledgment: Every grievance ticket is assigned a unique tracking number and acknowledged within 24 (twenty-four) hours.\n" +
                "• Resolution: Full investigation and disposal of the grievance occurs within 15 (fifteen) calendar days from receipt.\n" +
                "• Emergency Takedown: Complaints regarding non-consensual sexual content or impersonation are actioned and disabled within 24 hours of notification."
    )

    PolicySection(
        title = "3. Escalation & Appellate Mechanism",
        content = "If a data principal or user is dissatisfied with the Grievance Officer's resolution, an appeal may be preferred to the Grievance Appellate Committee (GAC) constituted by the Central Government under Rule 3A of the Information Technology Rules, 2021."
    )
}

@Composable
private fun PolicyHeader(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(bottom = 14.dp)) {
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DeepTeal
        )
        Text(
            text = subtitle,
            fontSize = 11.sp,
            color = TextSecondaryMuted
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = Color(0xFFE2E9EA))
    }
}

@Composable
private fun PolicySection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = DeepTeal
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            fontSize = 12.sp,
            lineHeight = 19.sp,
            color = TextPrimaryDark
        )
    }
}
