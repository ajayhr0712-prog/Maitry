package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SubscriptionPlan entity to store and track annual and 6-month subscription statuses
 * in the Room database, enabling the app to verify user access before displaying core content.
 */
@Entity(tableName = "subscription_plans")
data class SubscriptionPlan(
    @PrimaryKey
    val planId: String, // e.g. "PLAN_ANNUAL", "PLAN_6_MONTHS"
    val title: String, // "Annual Membership", "6-Month Membership"
    val durationMonths: Int, // 12 or 6
    val priceInr: Double, // Price in INR
    val gstRate: Double = 18.0, // Statutory 18% GST in India
    val isActive: Boolean = false, // Whether this plan is currently activated for the user
    val startDate: Long? = null, // Activation epoch timestamp in milliseconds
    val expiryDate: Long? = null, // Expiration epoch timestamp in milliseconds
    val autoRenew: Boolean = false,
    val paymentReferenceId: String? = null, // Razorpay payment ID
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Checks if this subscription plan currently grants active access.
     */
    fun isCurrentlyValid(currentTime: Long = System.currentTimeMillis()): Boolean {
        return isActive && (expiryDate == null || expiryDate > currentTime)
    }

    companion object {
        const val ID_ANNUAL = "PLAN_ANNUAL"
        const val ID_6_MONTHS = "PLAN_6_MONTHS"

        fun createDefaultPlans(): List<SubscriptionPlan> = listOf(
            SubscriptionPlan(
                planId = ID_ANNUAL,
                title = "12 Months Full Membership",
                durationMonths = 12,
                priceInr = 2999.0,
                isActive = false
            ),
            SubscriptionPlan(
                planId = ID_6_MONTHS,
                title = "6 Months Standard Access",
                durationMonths = 6,
                priceInr = 1799.0,
                isActive = false
            )
        )
    }
}
