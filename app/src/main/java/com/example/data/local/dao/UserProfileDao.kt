package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for handling CRUD operations on the [UserProfile] entity.
 */
@Dao
interface UserProfileDao {

    // ==========================================
    // CREATE / INSERT OPERATIONS
    // ==========================================

    /**
     * Insert or update a user profile. If a conflict occurs, replaces existing row.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfile): Long

    /**
     * Insert multiple user profiles in bulk.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<UserProfile>): List<Long>

    // ==========================================
    // READ / QUERY OPERATIONS
    // ==========================================

    /**
     * Observes the primary user profile as a reactive [Flow].
     */
    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    fun observeProfileById(id: Long = 1L): Flow<UserProfile?>

    /**
     * Synchronous / one-shot retrieval of a user profile by ID.
     */
    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    suspend fun getProfileById(id: Long = 1L): UserProfile?

    /**
     * Observes all user profiles stored locally.
     */
    @Query("SELECT * FROM user_profile ORDER BY updatedAt DESC")
    fun observeAllProfiles(): Flow<List<UserProfile>>

    /**
     * Retrieves all user profiles synchronously.
     */
    @Query("SELECT * FROM user_profile ORDER BY updatedAt DESC")
    suspend fun getAllProfiles(): List<UserProfile>

    /**
     * Retrieves user profiles filtered by subscription status (e.g. "ACTIVE", "EXPIRED", "INACTIVE").
     */
    @Query("SELECT * FROM user_profile WHERE subscriptionStatus = :status")
    fun observeProfilesBySubscriptionStatus(status: String): Flow<List<UserProfile>>

    /**
     * Counts the total number of profiles stored in the local database.
     */
    @Query("SELECT COUNT(*) FROM user_profile")
    suspend fun getProfileCount(): Int

    // ==========================================
    // UPDATE OPERATIONS
    // ==========================================

    /**
     * Updates an existing profile row.
     */
    @Update
    suspend fun updateProfile(profile: UserProfile): Int

    /**
     * Updates only the subscription status and subscribed boolean flag for a given profile ID.
     */
    @Query("UPDATE user_profile SET subscriptionStatus = :status, isSubscribed = :isSubscribed, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateSubscriptionStatus(
        id: Long = 1L,
        status: String,
        isSubscribed: Boolean,
        updatedAt: Long = System.currentTimeMillis()
    ): Int

    /**
     * Updates profile personal details (name, age, city, bio, interests).
     */
    @Query("""
        UPDATE user_profile 
        SET name = :name, age = :age, city = :city, bio = :bio, interests = :interests, updatedAt = :updatedAt 
        WHERE id = :id
    """)
    suspend fun updateProfileDetails(
        id: Long = 1L,
        name: String,
        age: Int,
        city: String,
        bio: String,
        interests: String,
        updatedAt: Long = System.currentTimeMillis()
    ): Int

    // ==========================================
    // DELETE OPERATIONS
    // ==========================================

    /**
     * Deletes a specific user profile entity.
     */
    @Delete
    suspend fun deleteProfile(profile: UserProfile): Int

    /**
     * Deletes a user profile by ID.
     */
    @Query("DELETE FROM user_profile WHERE id = :id")
    suspend fun deleteProfileById(id: Long = 1L): Int

    /**
     * Clears all user profiles from the table (used during data wipe/erasure under DPDP Act).
     */
    @Query("DELETE FROM user_profile")
    suspend fun deleteAllProfiles(): Int
}
