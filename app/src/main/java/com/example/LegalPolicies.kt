package com.example

/**
 * ============================================================================
 * LEGAL POLICIES & STATUTORY COMPLIANCE REPOSITORY
 * DRAFT FOR LEGAL REVIEW — IN ACCORDANCE WITH INDIAN IT & DPDP REGULATIONS
 * ============================================================================
 *
 * Applicable Indian Regulatory Framework:
 * 1. Information Technology Act, 2000 (IT Act 2000)
 * 2. Information Technology (Intermediary Guidelines and Digital Media Ethics Code) Rules, 2021 (IT Rules 2021)
 * 3. Digital Personal Data Protection Act, 2023 (DPDP Act 2023)
 * 4. Consumer Protection (E-Commerce) Rules, 2020
 *
 * Entity Details:
 * Data Fiduciary / Service Provider: Maitry Digital Trust Ltd
 * Operating Platform: Maitry (maitry.co.in)
 * Registered Jurisdiction: New Delhi, Republic of India
 */
object LegalPolicies {

    const val MANDATORY_NOTICE_HEADER =
        "DRAFT FOR LEGAL REVIEW — In accordance with Indian IT Act 2000 & DPDP Act 2023 regulations"

    const val DISCLAIMER_BANNER =
        "NOTICE: The documents below are regulatory compliance drafts prepared for independent legal evaluation and execution by Maitry Digital Trust Ltd's legal counsel."

    data class PolicySection(
        val title: String,
        val content: String
    )

    data class PolicyDocument(
        val name: String,
        val header: String,
        val statutoryBasis: String,
        val statusBadge: String = "Draft for legal review",
        val lastUpdated: String = "September 2026",
        val preamble: String,
        val sections: List<PolicySection>
    )

    // ========================================================================
    // 1. TERMS OF SERVICE
    // DRAFT FOR LEGAL REVIEW — IT ACT 2000 & IT RULES 2021
    // ========================================================================
    val termsOfService = PolicyDocument(
        name = "Terms of Service",
        header = "TERMS OF SERVICE — DRAFT FOR LEGAL REVIEW",
        statutoryBasis = "Information Technology Act, 2000 (Section 43A, 66D, 67) & IT Rules, 2021 (Rule 3)",
        statusBadge = "Draft for legal review",
        preamble = "DRAFT FOR LEGAL REVIEW — This document constitutes an electronic agreement in terms of the Information Technology Act, 2000 and the rules framed thereunder. By registering, accessing, browsing, or utilizing the services provided by Maitry ('Platform', 'Maitry', domain: maitry.co.in), operated by Maitry Digital Trust Ltd ('Company'), you unequivocally agree to be bound by the terms and conditions set forth herein.",
        sections = listOf(
            PolicySection(
                title = "1. Strict 18+ Age Gate & Eligibility",
                content = "[Draft for legal review] Maitry is exclusively dedicated to individuals aged 18 (eighteen) years or older. You represent, warrant, and covenant that you have completed 18 years of age on or before the date of registration. Registration by minors is strictly forbidden. The Company enforces automated birthdate verification. Any account found to belong to, or be operated on behalf of, a minor will be terminated immediately without refund."
            ),
            PolicySection(
                title = "2. Upfront Paid Subscription-Only Architecture",
                content = "[Draft for legal review] Maitry operates strictly on a subscription-only model with zero free tier. To protect our community from spam bots, commercial solicitations, catfishing, and casual luring, no user may browse member profiles, send invitations, connect, match, or chat without an active paid subscription. Subscriptions are billed as upfront non-recurring charges inclusive of 18% Goods & Services Tax (GST)."
            ),
            PolicySection(
                title = "3. Mobile OTP & Biometric Liveness Verification",
                content = "[Draft for legal review] Account creation requires two-factor identity authentication: (a) verification of an authentic Indian mobile telephone number (+91) via SMS One-Time Password (OTP); and (b) submission of a live biometric selfie liveness capture to confirm that the member matches their profile photos. Impersonation, using third-party photos, or fabricating credentials constitutes a cognizable cyber offence under Section 66D of the IT Act, 2000."
            ),
            PolicySection(
                title = "4. Automated Chat Protection & Anti-Off-Platform Protocol",
                content = "[Draft for legal review] To protect members against cyber extortion, financial fraud, and phishing, Maitry implements automated algorithmic message filters. Sharing 10-digit mobile numbers, UPI handles, payment links, and external messenger identifiers in initial communications is restricted. Photo sharing is locked by default and activates solely after mutual, simultaneous opt-in by both participating parties."
            ),
            PolicySection(
                title = "5. Prohibited Conduct & Zero Tolerance",
                content = "[Draft for legal review] Users are strictly prohibited from: (a) requesting, soliciting, or facilitating financial transactions, loans, gifts, investments, or cryptocurrency; (b) posting, transmitting, or soliciting commercial sex, escort work, or prostitution; (c) sharing non-consensual sexually explicit content; (d) engaging in blackmail, cyber-stalking, or extortion; (e) attempting to bypass automated safety filters."
            ),
            PolicySection(
                title = "6. Moderation Queue & Suspension Protocol",
                content = "[Draft for legal review] All member reports and automated safety flags are submitted to our 24-hour moderation queue. Maitry reserves the unilateral right to suspend, freeze, or permanently terminate any offending account without liability or refund in accordance with Rule 3 of the IT Rules, 2021."
            ),
            PolicySection(
                title = "7. Governing Law & Dispute Resolution",
                content = "[Draft for legal review] These Terms of Service shall be governed by, construed, and enforced in accordance with the laws of India. The courts of competent jurisdiction located at New Delhi, India shall have exclusive jurisdiction over all claims and proceedings arising out of or related to this Agreement."
            )
        )
    )

    // ========================================================================
    // 2. PRIVACY POLICY
    // DRAFT FOR LEGAL REVIEW — DPDP ACT 2023 COMPLIANT
    // ========================================================================
    val privacyPolicy = PolicyDocument(
        name = "Privacy Policy",
        header = "PRIVACY POLICY — DRAFT FOR LEGAL REVIEW (DPDP ACT 2023)",
        statutoryBasis = "Digital Personal Data Protection Act, 2023 (DPDP Act 2023) & IT (SPDI) Rules, 2011",
        statusBadge = "Draft for legal review",
        preamble = "DRAFT FOR LEGAL REVIEW — Maitry Digital Trust Ltd ('Data Fiduciary') is committed to protecting the privacy, integrity, and autonomy of your personal data in strict compliance with the Digital Personal Data Protection Act, 2023 ('DPDP Act 2023') and the Information Technology (Reasonable Security Practices and Procedures and Sensitive Personal Data or Information) Rules, 2011.",
        sections = listOf(
            PolicySection(
                title = "1. Identity & Registered Office of Data Fiduciary",
                content = "[Draft for legal review] Entity Name: Maitry Digital Trust Ltd\nPlatform Domain: maitry.co.in\nRegistered Address: 402 Barakhamba Road, Connaught Place, New Delhi - 110001, India\nGrievance Desk Email: grievance@maitry.co.in"
            ),
            PolicySection(
                title = "2. Specific Categories of Personal Data Collected",
                content = "[Draft for legal review] We collect only personal data necessary for matchmaking and anti-fraud safeguards: (a) Account Identifiers: Indian mobile phone number (+91) and device tokens; (b) Profile Attributes: Display name, age, birthdate, gender, city, intent, bio, interests, and spoken languages; (c) Verification Data: Real-time selfie images captured solely for facial liveness match; (d) Transaction Data: Payment identifiers from Razorpay, invoice history, and GST state of supply. Full debit/credit card credentials and UPI PINs are never collected or stored."
            ),
            PolicySection(
                title = "3. Lawful Basis & Purpose Limitation (Section 4 & 5, DPDP Act)",
                content = "[Draft for legal review] Personal data is processed exclusively for explicit purposes: (a) Verifying age (18+) and authentic identity; (b) Delivering curated daily compatibility recommendations; (c) Preventing harassment, extortion, and off-platform abuse; (d) Generating statutory GST tax invoices under the Central Goods and Services Tax Act, 2017. Maitry never sells, leases, or trades user databases to advertisers or commercial brokers."
            ),
            PolicySection(
                title = "4. Strict 90-Day Retention Schedule & Automated Purge",
                content = "[Draft for legal review] Active member records are maintained during valid subscription terms. When a subscription expires, matches and profile records are retained in frozen status for exactly 90 (ninety) calendar days to facilitate renewal. Upon expiry of 90 days without renewal, or upon the user's explicit request for account erasure, all personal identifiers, chats, and biometric verification data are irreversibly purged from our active databases."
            ),
            PolicySection(
                title = "5. Statutory Rights of Data Principals",
                content = "[Draft for legal review] As a Data Principal under Section 11, 12, and 13 of the DPDP Act 2023, you hold the following rights: (a) Right to Access: Obtain a summary of personal data processed; (b) Right to Data Export: Export your personal data bundle in machine-readable JSON format via the Account dashboard; (c) Right to Erasure: Trigger instant account purge and deletion; (d) Right of Grievance Redressal: File complaints directly with our statutory Grievance Officer."
            ),
            PolicySection(
                title = "6. Security Architecture & Breach Notification",
                content = "[Draft for legal review] All personal data is encrypted in transit using TLS 1.3 and at rest utilizing AES-256 cryptographic standards within ISO 27001-certified Indian data centers. In the event of a personal data security breach, the Data Fiduciary will notify the Data Protection Board of India and affected data principals without undue delay."
            )
        )
    )

    // ========================================================================
    // 3. REFUND & CANCELLATION POLICY
    // DRAFT FOR LEGAL REVIEW — CONSUMER PROTECTION RULES 2020
    // ========================================================================
    val refundPolicy = PolicyDocument(
        name = "Refund and Cancellation Policy",
        header = "REFUND & CANCELLATION POLICY — DRAFT FOR LEGAL REVIEW",
        statutoryBasis = "Consumer Protection (E-Commerce) Rules, 2020 & CGST Act, 2017",
        statusBadge = "Draft for legal review",
        preamble = "DRAFT FOR LEGAL REVIEW — This Refund, Cancellation and Billing Policy governs all payment transactions and subscription plans on Maitry (maitry.co.in) operated by Maitry Digital Trust Ltd.",
        sections = listOf(
            PolicySection(
                title = "1. Upfront Pricing Transparency & GST Details",
                content = "[Draft for legal review] All membership plans clearly disclose the base service charge plus 18% Goods & Services Tax (GST) under Services Accounting Code (SAC) 998431 prior to payment confirmation:\n" +
                        "• Annual Plan: ₹999 total (Base ₹846.61 + 18% GST ₹152.39)\n" +
                        "• 6-Month Plan: ₹599 total (Base ₹507.63 + 18% GST ₹91.37)\n" +
                        "• 1-Month Plan: ₹199 total (Base ₹168.64 + 18% GST ₹30.36)\n" +
                        "There are zero hidden costs, processing surcharges, or unstated fees."
            ),
            PolicySection(
                title = "2. Strict Non-Recurring / NO Auto-Renewal Guarantee",
                content = "[Draft for legal review] Maitry operates strictly on a one-time non-recurring billing model. The Company does not establish recurring UPI auto-debit mandates or store credit/debit card numbers for automated rebilling. Subscriptions expire automatically unless the user affirmatively elects to renew."
            ),
            PolicySection(
                title = "3. 7-Day Fair Window Refund Protocol",
                content = "[Draft for legal review] A subscriber may request a full refund within 7 (seven) calendar days of initial payment, provided that: (a) The member experienced verified technical defects preventing access; and (b) The member has not initiated or exchanged any chat messages on the platform. Refund requests must be directed to billing@maitry.co.in citing the GST Tax Invoice number."
            ),
            PolicySection(
                title = "4. Disqualification from Refunds",
                content = "[Draft for legal review] Refunds will not be granted under any circumstance where an account is suspended or banned for violating Community Guidelines, anti-harassment rules, commercial solicitation prohibitions, or underage restrictions."
            ),
            PolicySection(
                title = "5. Statutory GST Invoicing & Gateway Verification",
                content = "[Draft for legal review] Payments are processed securely via RBI-authorized aggregators (Razorpay). Each confirmed transaction generates a GST Tax Invoice featuring the Company's GSTIN, state of supply, and CGST/SGST or IGST itemization. Approved refunds are credited to the source instrument within 5 to 7 business days."
            )
        )
    )

    // ========================================================================
    // 4. COMMUNITY GUIDELINES & ANTI-SEXTORTION CHARTER
    // DRAFT FOR LEGAL REVIEW — IT RULES 2021 & BNS COMPLIANCE
    // ========================================================================
    val communityGuidelines = PolicyDocument(
        name = "Community Guidelines & Safety Charter",
        header = "COMMUNITY GUIDELINES & ANTI-SEXTORTION CHARTER — DRAFT FOR LEGAL REVIEW",
        statutoryBasis = "Rule 3(1)(b) of IT Rules, 2021 & Bharatiya Nyaya Sanhita (BNS)",
        statusBadge = "Draft for legal review",
        preamble = "DRAFT FOR LEGAL REVIEW — Maitry is created for urban Indians who value intentional, dignified connections starting from honest friendship. Every member agrees to uphold these standards. Violations result in immediate permanent expulsion.",
        sections = listOf(
            PolicySection(
                title = "1. The Friendship-First Ethos & Courteous Pacing",
                content = "[Draft for legal review] Maitry prioritizes emotional maturity, slow dialogue, and mutual respect. Aggressive sexual propositioning, entitled behavior, vulgar language, and pressuring members for rapid offline meetings violate our community charter."
            ),
            PolicySection(
                title = "2. Absolute Zero Tolerance for Financial Solicitation & Fraud",
                content = "[Draft for legal review] You must never ask any connection for money, loans, UPI transfers, donations, gift cards, or investments. Soliciting money or sharing banking coordinates results in immediate permanent telephone blacklisting and referral to the National Cyber Crime Reporting Portal (cybercrime.gov.in)."
            ),
            PolicySection(
                title = "3. Anti-Sextortion & Non-Consensual Imagery Protection",
                content = "[Draft for legal review] Sextortion, cyber-blackmail, and the transmission of non-consensual sexual content are severe criminal offences under Section 66E and 67 of the IT Act, 2000 and Section 384/354A of BNS/IPC. Photos cannot be exchanged in chat until both parties have explicitly toggled opt-in consent."
            ),
            PolicySection(
                title = "4. Safe Offline Meeting Protocol in Public Venues",
                content = "[Draft for legal review] When meeting in person: (a) Always meet in populated, well-lit public places (e.g. established cafes or tea houses); (b) Inform trusted friends or family of your venue and plans; (c) Arrange independent transportation; (d) Never disclose home residences or private financial details prematurely."
            ),
            PolicySection(
                title = "5. Designated Statutory Grievance Officer",
                content = "[Draft for legal review] Officer: Adv. Ramesh V. Kulkarni\nEntity: Maitry Digital Trust Ltd (maitry.co.in)\nAddress: 402 Barakhamba Road, Connaught Place, New Delhi - 110001, India\nEmail: grievance@maitry.co.in\nPhone: +91 11 4982 3000\nSLA: Acknowledgment within 24 hours, formal disposal within 15 days."
            )
        )
    )

    /**
     * All statutory policy documents for easy iteration and UI rendering.
     */
    val allPolicies: List<PolicyDocument> = listOf(
        termsOfService,
        privacyPolicy,
        refundPolicy,
        communityGuidelines
    )

    /**
     * Full plain-text dump of all policies with prominent headers, suitable for export or terminal review.
     */
    fun getFullLegalPoliciesText(): String {
        return buildString {
            appendLine("================================================================================")
            appendLine(MANDATORY_NOTICE_HEADER)
            appendLine(DISCLAIMER_BANNER)
            appendLine("================================================================================\n")
            allPolicies.forEach { policy ->
                appendLine("--------------------------------------------------------------------------------")
                appendLine(policy.header)
                appendLine("Statutory Authority: ${policy.statutoryBasis}")
                appendLine("Status: ${policy.statusBadge} | Last Updated: ${policy.lastUpdated}")
                appendLine("--------------------------------------------------------------------------------")
                appendLine(policy.preamble)
                appendLine()
                policy.sections.forEach { section ->
                    appendLine(section.title)
                    appendLine(section.content)
                    appendLine()
                }
            }
        }
    }
}
