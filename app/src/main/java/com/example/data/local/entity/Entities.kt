package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account")
data class UserAccountEntity(
    @PrimaryKey val id: Int = 1,
    val phoneNumber: String = "",
    val name: String = "",
    val age: Int = 0,
    val birthdate: String = "",
    val gender: String = "Woman",
    val city: String = "Mumbai",
    val bio: String = "",
    val intent: String = "Friendship leading to relationship",
    val interests: String = "Chai Conversations, Books & Poetry, Indie Music, Weekend Treks",
    val languages: String = "Hindi, English",
    val avatarUri: String = "",
    val verificationStatus: String = "NONE", // NONE, PENDING, VERIFIED
    val idVerified: Boolean = false,
    val isSubscribed: Boolean = false, // True ONLY after successful subscription payment
    val activePlanId: String = "", // ANNUAL_999, HALF_YEAR_599, MONTHLY_199
    val planName: String = "",
    val subscriptionStartDate: Long = 0L,
    val subscriptionExpiryDate: Long = 0L,
    val autoRenewal: Boolean = false, // Strictly NO auto-renewal
    val isProfileHidden: Boolean = false,
    val isAccountPaused: Boolean = false,
    val dpdpConsentGiven: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "curated_matches")
data class CuratedMatchEntity(
    @PrimaryKey val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val city: String,
    val profession: String,
    val bio: String,
    val intent: String,
    val interests: String,
    val languages: String,
    val icebreakerQuestion: String,
    val whyMaitryAnswer: String,
    val isVerified: Boolean = true,
    val connectionStatus: String = "NONE", // NONE, SENT_HELLO, MUTUAL_MATCH, PASSED
    val photoOptInMe: Boolean = false,
    val photoOptInOther: Boolean = false,
    val accentColorHex: Long = 0xFF0D3B43
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val matchId: String,
    val sender: String, // ME, OTHER, SYSTEM
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isImage: Boolean = false,
    val isSafetyWarning: Boolean = false,
    val warningReason: String? = null
)

@Entity(tableName = "invoices")
data class InvoiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val invoiceNumber: String,
    val planName: String,
    val issueDate: String,
    val expiryDate: String,
    val customerName: String,
    val customerPhone: String,
    val customerCity: String,
    val customerState: String,
    val hsnSacCode: String = "998431",
    val baseAmount: Double,
    val gstRate: Double = 18.0,
    val cgstAmount: Double,
    val sgstAmount: Double,
    val igstAmount: Double = 0.0,
    val totalAmount: Double,
    val paymentMethod: String,
    val razorpayPaymentId: String,
    val status: String = "PAID"
)

@Entity(tableName = "safety_reports")
data class SafetyReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reportedMatchId: String,
    val reportedName: String,
    val reason: String,
    val details: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "SUBMITTED_TO_24H_QUEUE"
)

@Entity(tableName = "platform_config")
data class PlatformConfigEntity(
    @PrimaryKey val id: Int = 1,
    val annualPrice: Int = 999,
    val sixMonthPrice: Int = 599,
    val monthlyPrice: Int = 199,
    val supportEmail: String = "grievance@maitry.co.in",
    val grievanceOfficer: String = "Adv. Ramesh V. Kulkarni",
    val grievanceAddress: String = "Maitry Digital Trust Ltd, 402 Barakhamba Road, Connaught Place, New Delhi - 110001",
    val lastUpdated: Long = System.currentTimeMillis()
)
