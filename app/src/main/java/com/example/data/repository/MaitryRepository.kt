package com.example.data.repository

import com.example.data.local.dao.MaitryDao
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.CuratedMatchEntity
import com.example.data.local.entity.InvoiceEntity
import com.example.data.local.entity.PlatformConfigEntity
import com.example.data.local.entity.SafetyReportEntity
import com.example.data.local.entity.UserAccountEntity
import com.example.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class MaitryRepository(private val dao: MaitryDao) {

    val currentUser: Flow<UserAccountEntity?> = dao.getUser()
    val userProfile: Flow<UserProfile?> = dao.getUserProfile()
    val curatedMatches: Flow<List<CuratedMatchEntity>> = dao.getAllMatches()
    val allInvoices: Flow<List<InvoiceEntity>> = dao.getAllInvoices()
    val allReports: Flow<List<SafetyReportEntity>> = dao.getAllReports()
    val platformConfig: Flow<PlatformConfigEntity?> = dao.getConfig()

    suspend fun getCuratedMatchByIdSync(id: String): CuratedMatchEntity? {
        return dao.getMatchByIdSync(id)
    }

    fun getChatMessages(matchId: String): Flow<List<ChatMessageEntity>> {
        return dao.getChatMessages(matchId)
    }

    suspend fun initializeSeedDataIfEmpty() {
        // Initialize config
        dao.insertOrUpdateConfig(
            PlatformConfigEntity(
                id = 1,
                annualPrice = 999,
                sixMonthPrice = 599,
                monthlyPrice = 199,
                supportEmail = "grievance@maitry.co.in",
                grievanceOfficer = "Adv. Ramesh V. Kulkarni"
            )
        )

        // Seed realistic, respectful, diverse Indian profiles
        val initialMatches = listOf(
            CuratedMatchEntity(
                id = "ananya_28",
                name = "Ananya Sharma",
                age = 28,
                gender = "Woman",
                city = "Bengaluru",
                profession = "Urban Architect & Sketch Artist",
                bio = "Designing calm public spaces by day, hunting for the best filter coffee and old bookstores on weekends. Believer in slow talks, emotional maturity, and growing from quiet friendship.",
                intent = "Friendship leading to relationship",
                interests = "Filter Coffee, Books & Poetry, Heritage Walks, Sketching, Indie Music",
                languages = "English, Hindi, Kannada",
                icebreakerQuestion = "What is a small neighborhood corner in your city that feels like home to you?",
                whyMaitryAnswer = "Swiping apps feel like endless catalogs. I want one genuine conversation with someone who values depth over quick dopamine.",
                isVerified = true,
                connectionStatus = "NONE",
                accentColorHex = 0xFF14535D
            ),
            CuratedMatchEntity(
                id = "rohit_32",
                name = "Rohit Deshmukh",
                age = 32,
                gender = "Man",
                city = "Pune",
                profession = "Sustainable Agri-Tech Specialist",
                bio = "Born in Nashik, now building soil tech in Pune. Weekends mean western ghats cycling, cooking Khandeshi curries, and listening to classical sitar. Looking for warmth and genuine companionship.",
                intent = "Friendship leading to relationship",
                interests = "Ghats Cycling, Hindustani Classical, Cooking, Agro-Forestry, Trekking",
                languages = "Marathi, Hindi, English",
                icebreakerQuestion = "Sweet masala chai or black Irani chai with bun maska?",
                whyMaitryAnswer = "Honesty and peace matter the most to me. Starting as friends takes the artificial pressure off.",
                isVerified = true,
                connectionStatus = "MUTUAL_MATCH", // Seeded initial mutual connection to test chat
                accentColorHex = 0xFFD44832
            ),
            CuratedMatchEntity(
                id = "priya_38",
                name = "Priya Nambiar",
                age = 38,
                gender = "Woman",
                city = "Mumbai",
                profession = "Documentary Producer & Writer",
                bio = "Second innings seeker. Mother to a golden retriever named Ginger. I enjoy seaside morning walks at Bandra, Marathi theatre, world cinema, and laughing over life's absurdities.",
                intent = "Friendship / Second Innings",
                interests = "Seaside Walks, World Cinema, Marathi Theatre, Dogs, Food Walks",
                languages = "English, Hindi, Malayalam, Marathi",
                icebreakerQuestion = "Which film has made you laugh until your stomach hurt?",
                whyMaitryAnswer = "At 38, I don't want matrimonial pressure or shallow hookup culture. I want a partner who is first a companion and confidant.",
                isVerified = true,
                connectionStatus = "NONE",
                accentColorHex = 0xFF0D3B43
            ),
            CuratedMatchEntity(
                id = "kavita_26",
                name = "Kavita Sen",
                age = 26,
                gender = "Woman",
                city = "Kolkata",
                profession = "Classical Musician & English Lecturer",
                bio = "Rabindrasangeet enthusiast, lover of rain, old tram routes, and deep late-night debates about philosophy and art. Soft-spoken but passionate about values.",
                intent = "Friendship first",
                interests = "Rabindrasangeet, Old Tramways, Literature, Monsoon Poetry, Tea Walks",
                languages = "Bengali, Hindi, English",
                icebreakerQuestion = "What is your comfort song on a rainy evening?",
                whyMaitryAnswer = "I believe love cannot exist without profound friendship and intellectual respect.",
                isVerified = true,
                connectionStatus = "NONE",
                accentColorHex = 0xFF18535D
            ),
            CuratedMatchEntity(
                id = "arjun_35",
                name = "Arjun Mehra",
                age = 35,
                gender = "Man",
                city = "Delhi NCR",
                profession = "Social Impact Lawyer & Marathon Runner",
                bio = "Working on public health advocacy. Ran the Delhi half marathon twice. Calm temperament, lover of Ghalib's poetry, Sufi music, and quiet Sunday brunches.",
                intent = "Serious relationship",
                interests = "Marathons, Sufi Music, Urdu Poetry, Public Policy, Coffee",
                languages = "Hindi, English, Punjabi",
                icebreakerQuestion = "Morning run in Lodhi Gardens or evening walk at India Gate?",
                whyMaitryAnswer = "The subscription barrier keeps out catfishes and time-wasters. It shows everyone here is intentional.",
                isVerified = true,
                connectionStatus = "NONE",
                accentColorHex = 0xFF2A5056
            ),
            CuratedMatchEntity(
                id = "meera_41",
                name = "Dr. Meera Iyer",
                age = 41,
                gender = "Woman",
                city = "Hyderabad",
                profession = "Cardiologist & Amateur Botanist",
                bio = "Balancing intensive hospital shifts with caring for over 60 houseplants. Divorced 4 years ago, ready with an open heart for meaningful companionship built on empathy and trust.",
                intent = "Friendship / Second Innings",
                interests = "Indoor Botany, Classical Carnatic, Medicine & Wellness, Travel, South Indian Filter Kaapi",
                languages = "Tamil, Telugu, English, Hindi",
                icebreakerQuestion = "What is one daily ritual that brings you complete peace of mind?",
                whyMaitryAnswer = "Second-chance seekers deserve a safe, dignified space without judgment. Maitry's subscription filter creates respect.",
                isVerified = true,
                connectionStatus = "NONE",
                accentColorHex = 0xFF14535D
            )
        )
        dao.insertMatchesIfAbsent(initialMatches)

        // Seed sample chat message for the seeded mutual match
        val sampleChat = listOf(
            ChatMessageEntity(
                matchId = "rohit_32",
                sender = "OTHER",
                text = "Namaste! Delighted to connect with you on Maitry. I saw you enjoy weekend treks and good books!",
                timestamp = System.currentTimeMillis() - 3600000 * 2
            ),
            ChatMessageEntity(
                matchId = "rohit_32",
                sender = "OTHER",
                text = "What is your favorite weekend getaway spot around here?",
                timestamp = System.currentTimeMillis() - 3600000
            )
        )
        for (msg in sampleChat) {
            dao.insertChatMessage(msg)
        }
    }

    // --- User & Onboarding ---
    suspend fun saveUserProfile(
        phone: String,
        name: String,
        age: Int,
        birthdate: String,
        gender: String,
        city: String,
        bio: String,
        intent: String,
        interests: String,
        languages: String
    ) {
        val existing = dao.getUserSync()
        val user = existing?.copy(
            phoneNumber = phone,
            name = name,
            age = age,
            birthdate = birthdate,
            gender = gender,
            city = city,
            bio = bio,
            intent = intent,
            interests = interests,
            languages = languages
        ) ?: UserAccountEntity(
            phoneNumber = phone,
            name = name,
            age = age,
            birthdate = birthdate,
            gender = gender,
            city = city,
            bio = bio,
            intent = intent,
            interests = interests,
            languages = languages,
            verificationStatus = "NONE",
            isSubscribed = false
        )
        dao.insertOrUpdateUser(user)

        // Store user profile locally in UserProfile entity
        val profile = UserProfile(
            id = 1L,
            name = name,
            age = age,
            city = city,
            bio = bio,
            interests = interests,
            subscriptionStatus = if (existing?.isSubscribed == true) "ACTIVE" else "INACTIVE",
            isSubscribed = existing?.isSubscribed ?: false
        )
        dao.insertOrUpdateUserProfile(profile)
    }

    suspend fun submitVerification(isSelfieSubmitted: Boolean, isIdSubmitted: Boolean) {
        // Mark as verified
        val status = if (isSelfieSubmitted) "VERIFIED" else "PENDING"
        dao.updateVerification(status, isIdSubmitted)
    }

    // --- Subscription & Razorpay Payment Simulation ---
    suspend fun processSubscriptionPayment(
        planId: String,
        planName: String,
        amount: Double,
        durationDays: Int,
        paymentMethod: String,
        customerName: String,
        customerPhone: String,
        customerCity: String,
        customerState: String
    ): InvoiceEntity {
        val now = System.currentTimeMillis()
        val expiryTime = now + (durationDays.toLong() * 24L * 60L * 60L * 1000L)

        // 18% GST calculation (GST inclusive formula: Base = Total / 1.18)
        val baseAmount = Math.round((amount / 1.18) * 100.0) / 100.0
        val totalGst = Math.round((amount - baseAmount) * 100.0) / 100.0
        val isMaharashtra = customerState.equals("Maharashtra", ignoreCase = true)
        val cgst = if (isMaharashtra) Math.round((totalGst / 2.0) * 100.0) / 100.0 else 0.0
        val sgst = if (isMaharashtra) Math.round((totalGst / 2.0) * 100.0) / 100.0 else 0.0
        val igst = if (!isMaharashtra) totalGst else 0.0

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val issueDateStr = dateFormat.format(Date(now))
        val expiryDateStr = dateFormat.format(Date(expiryTime))
        val invoiceNum = "MAITRY/2026-27/${(1000..9999).random()}"
        val razorpayId = "pay_rzp_${UUID.randomUUID().toString().substring(0, 12)}"

        val invoice = InvoiceEntity(
            invoiceNumber = invoiceNum,
            planName = planName,
            issueDate = issueDateStr,
            expiryDate = expiryDateStr,
            customerName = customerName,
            customerPhone = customerPhone,
            customerCity = customerCity,
            customerState = customerState,
            hsnSacCode = "998431",
            baseAmount = baseAmount,
            gstRate = 18.0,
            cgstAmount = cgst,
            sgstAmount = sgst,
            igstAmount = igst,
            totalAmount = amount,
            paymentMethod = paymentMethod,
            razorpayPaymentId = razorpayId,
            status = "PAID"
        )

        dao.insertInvoice(invoice)
        dao.updateSubscription(
            isSubscribed = true,
            planId = planId,
            planName = planName,
            startDate = now,
            expiryDate = expiryTime
        )

        val existingProfile = dao.getUserProfileSync(1L)
        if (existingProfile != null) {
            dao.insertOrUpdateUserProfile(
                existingProfile.copy(
                    subscriptionStatus = "ACTIVE",
                    isSubscribed = true,
                    updatedAt = now
                )
            )
        } else {
            dao.insertOrUpdateUserProfile(
                UserProfile(
                    id = 1L,
                    name = customerName,
                    city = customerCity,
                    subscriptionStatus = "ACTIVE",
                    isSubscribed = true,
                    updatedAt = now
                )
            )
        }

        return invoice
    }

    suspend fun expireSubscriptionForTesting() {
        val now = System.currentTimeMillis() - 10000L
        dao.updateSubscription(
            isSubscribed = false,
            planId = "EXPIRED",
            planName = "Expired Subscription",
            startDate = now - 365L * 24L * 60L * 60L * 1000L,
            expiryDate = now
        )
        val existingProfile = dao.getUserProfileSync(1L)
        if (existingProfile != null) {
            dao.insertOrUpdateUserProfile(
                existingProfile.copy(
                    subscriptionStatus = "EXPIRED",
                    isSubscribed = false,
                    updatedAt = now
                )
            )
        }
    }

    // --- Safety and Message Validation ---
    data class SafetyCheckResult(
        val isBlocked: Boolean,
        val violationReason: String? = null
    )

    fun validateMessageSafety(text: String): SafetyCheckResult {
        // Block 10-digit phone numbers and phone patterns (+91 etc.)
        val phoneRegex = Regex("""(\+?91[\-\s]?)?[6-9]\d{9}|\b\d{5}[\s\-]?\d{5}\b|\b\d{10}\b""")
        if (phoneRegex.containsMatchIn(text)) {
            return SafetyCheckResult(
                isBlocked = true,
                violationReason = "For your safety, sharing mobile numbers in early chat is strictly prohibited. Keep conversations inside Maitry."
            )
        }

        // Block external links, socials and messaging apps
        val linkRegex = Regex("""(https?://|www\.|\.com|\.in|\.org|\.co|wa\.me|t\.me|telegram|whatsapp|insta|snapchat)""", RegexOption.IGNORE_CASE)
        if (linkRegex.containsMatchIn(text)) {
            return SafetyCheckResult(
                isBlocked = true,
                violationReason = "External links and off-platform messenger handles are blocked to protect you from phishing and scams."
            )
        }

        // Block monetary requests, UPI handles, extortion keywords
        val financialRegex = Regex("""(upi|gpay|paytm|phonepe|send money|transfer money|cryptocurrency|investment|escort|hookup|rate card)""", RegexOption.IGNORE_CASE)
        if (financialRegex.containsMatchIn(text)) {
            return SafetyCheckResult(
                isBlocked = true,
                violationReason = "Financial requests, commercial solicitations, and inappropriate keywords violate Maitry zero-tolerance policy."
            )
        }

        return SafetyCheckResult(isBlocked = false)
    }

    suspend fun sendMessage(matchId: String, text: String, sender: String = "ME") {
        val message = ChatMessageEntity(
            matchId = matchId,
            sender = sender,
            text = text,
            timestamp = System.currentTimeMillis()
        )
        dao.insertChatMessage(message)
    }

    suspend fun connectWithProfile(matchId: String) {
        dao.updateConnectionStatus(matchId, "MUTUAL_MATCH")
    }

    suspend fun passProfile(matchId: String) {
        dao.updateConnectionStatus(matchId, "PASSED")
    }

    suspend fun optInPhotoSharing(matchId: String, optIn: Boolean) {
        dao.updatePhotoOptIn(matchId, optIn)
    }

    // --- Safety Center & Reporting ---
    suspend fun submitReport(matchId: String, matchName: String, reason: String, details: String) {
        val report = SafetyReportEntity(
            reportedMatchId = matchId,
            reportedName = matchName,
            reason = reason,
            details = details,
            timestamp = System.currentTimeMillis(),
            status = "SUBMITTED_TO_24H_QUEUE"
        )
        dao.insertReport(report)
        // Auto block / remove from active list
        dao.updateConnectionStatus(matchId, "PASSED")
    }

    suspend fun blockUser(matchId: String) {
        dao.updateConnectionStatus(matchId, "BLOCKED")
        dao.clearChat(matchId)
    }

    suspend fun setProfileVisibility(isHidden: Boolean) {
        dao.updateProfileHidden(isHidden)
    }

    suspend fun setAccountPause(isPaused: Boolean) {
        dao.updateAccountPaused(isPaused)
    }

    // --- DPDP Act 2023 Data Export & Deletion ---
    suspend fun exportUserDataJson(): String {
        val user = dao.getUserSync()
        val invoices = dao.getAllInvoices()
        return buildString {
            append("{\n")
            append("  \"data_controller\": \"Maitry Digital Trust (maitry.co.in)\",\n")
            append("  \"act_compliance\": \"Digital Personal Data Protection Act (DPDP) 2023\",\n")
            append("  \"export_timestamp\": \"${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date())}\",\n")
            append("  \"user_profile\": {\n")
            append("    \"name\": \"${user?.name ?: "N/A"}\",\n")
            append("    \"phone\": \"${user?.phoneNumber ?: "N/A"}\",\n")
            append("    \"age\": ${user?.age ?: 0},\n")
            append("    \"city\": \"${user?.city ?: "N/A"}\",\n")
            append("    \"intent\": \"${user?.intent ?: "N/A"}\",\n")
            append("    \"verified_status\": \"${user?.verificationStatus ?: "NONE"}\",\n")
            append("    \"subscription_active\": ${user?.isSubscribed ?: false}\n")
            append("  },\n")
            append("  \"purpose_limitation\": \"Data collected solely for verified match recommendation & chat moderation.\",\n")
            append("  \"data_retention_days\": 90,\n")
            append("  \"status\": \"ACTIVE_USER\"\n")
            append("}")
        }
    }

    suspend fun purgeAllUserData() {
        dao.clearUser()
    }

    // --- Admin Panel Controls ---
    suspend fun updateConfigPrices(annual: Int, sixMonth: Int, monthly: Int) {
        dao.updatePricingConfig(annual, sixMonth, monthly)
    }

    suspend fun resolveReport(reportId: Long, action: String) {
        dao.updateReportStatus(reportId, action)
    }
}
