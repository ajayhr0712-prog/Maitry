package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity class 'UserProfile' to store user data locally for the app.
 *
 * Includes fields for:
 * - name: Full or display name of the user
 * - age: Age in years (18+ statutory requirement)
 * - city: Current residing city (e.g. Mumbai, Bengaluru, Pune)
 * - bio: Self-written personal summary or philosophy
 * - interests: Comma-separated or tagged interests
 * - subscriptionStatus: Active subscription tier or status (e.g. "ACTIVE", "EXPIRED", "INACTIVE")
 * - isSubscribed: Boolean flag for active subscription
 * - photoUrls: Comma-separated list of photo URLs or local asset indicators
 */
@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Long = 1L,
    val name: String = "",
    val age: Int = 0,
    val city: String = "",
    val bio: String = "",
    val interests: String = "",
    val subscriptionStatus: String = "INACTIVE", // "ACTIVE", "EXPIRED", "INACTIVE"
    val isSubscribed: Boolean = false,
    val photoUrls: String = "", // Comma-separated photo URIs or image identifiers
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Parses comma-separated photo URLs into a clean list of image strings.
     */
    fun getPhotoList(): List<String> {
        if (photoUrls.isBlank()) return emptyList()
        return photoUrls.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }
}
