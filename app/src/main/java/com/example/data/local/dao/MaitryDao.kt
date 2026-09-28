package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.CuratedMatchEntity
import com.example.data.local.entity.InvoiceEntity
import com.example.data.local.entity.PlatformConfigEntity
import com.example.data.local.entity.SafetyReportEntity
import com.example.data.local.entity.UserAccountEntity
import com.example.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface MaitryDao {

    // --- User Profile (Room Entity) ---
    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    fun getUserProfile(id: Long = 1L): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    suspend fun getUserProfileSync(id: Long = 1L): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(profile: UserProfile)

    @Query("DELETE FROM user_profile WHERE id = :id")
    suspend fun clearUserProfile(id: Long = 1L)

    // --- User Account ---
    @Query("SELECT * FROM user_account WHERE id = 1 LIMIT 1")
    fun getUser(): Flow<UserAccountEntity?>

    @Query("SELECT * FROM user_account WHERE id = 1 LIMIT 1")
    suspend fun getUserSync(): UserAccountEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserAccountEntity)

    @Query("UPDATE user_account SET isSubscribed = :isSubscribed, activePlanId = :planId, planName = :planName, subscriptionStartDate = :startDate, subscriptionExpiryDate = :expiryDate WHERE id = 1")
    suspend fun updateSubscription(isSubscribed: Boolean, planId: String, planName: String, startDate: Long, expiryDate: Long)

    @Query("UPDATE user_account SET verificationStatus = :status, idVerified = :idVerified WHERE id = 1")
    suspend fun updateVerification(status: String, idVerified: Boolean)

    @Query("UPDATE user_account SET isProfileHidden = :isHidden WHERE id = 1")
    suspend fun updateProfileHidden(isHidden: Boolean)

    @Query("UPDATE user_account SET isAccountPaused = :isPaused WHERE id = 1")
    suspend fun updateAccountPaused(isPaused: Boolean)

    @Query("DELETE FROM user_account WHERE id = 1")
    suspend fun clearUser()

    // --- Curated Matches ---
    @Query("SELECT * FROM curated_matches")
    fun getAllMatches(): Flow<List<CuratedMatchEntity>>

    @Query("SELECT * FROM curated_matches WHERE id = :id LIMIT 1")
    fun getMatchById(id: String): Flow<CuratedMatchEntity?>

    @Query("SELECT * FROM curated_matches WHERE id = :id LIMIT 1")
    suspend fun getMatchByIdSync(id: String): CuratedMatchEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMatchesIfAbsent(matches: List<CuratedMatchEntity>)

    @Query("UPDATE curated_matches SET connectionStatus = :status WHERE id = :matchId")
    suspend fun updateConnectionStatus(matchId: String, status: String)

    @Query("UPDATE curated_matches SET photoOptInMe = :optInMe WHERE id = :matchId")
    suspend fun updatePhotoOptIn(matchId: String, optInMe: Boolean)

    @Query("DELETE FROM curated_matches WHERE id = :matchId")
    suspend fun removeMatch(matchId: String)

    // --- Chat Messages ---
    @Query("SELECT * FROM chat_messages WHERE matchId = :matchId ORDER BY timestamp ASC")
    fun getChatMessages(matchId: String): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages WHERE matchId = :matchId")
    suspend fun clearChat(matchId: String)

    // --- Invoices ---
    @Query("SELECT * FROM invoices ORDER BY id DESC")
    fun getAllInvoices(): Flow<List<InvoiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: InvoiceEntity)

    // --- Safety Reports ---
    @Query("SELECT * FROM safety_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<SafetyReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: SafetyReportEntity)

    @Query("UPDATE safety_reports SET status = :status WHERE id = :reportId")
    suspend fun updateReportStatus(reportId: Long, status: String)

    // --- Platform Config ---
    @Query("SELECT * FROM platform_config WHERE id = 1 LIMIT 1")
    fun getConfig(): Flow<PlatformConfigEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateConfig(config: PlatformConfigEntity)

    @Query("UPDATE platform_config SET annualPrice = :annual, sixMonthPrice = :sixMonth, monthlyPrice = :monthly WHERE id = 1")
    suspend fun updatePricingConfig(annual: Int, sixMonth: Int, monthly: Int)
}
