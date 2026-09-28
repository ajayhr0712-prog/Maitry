package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.local.entity.SubscriptionPlan
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for storing, updating, and querying subscription plans
 * (annual and 6-month) to verify user access before displaying core app content.
 */
@Dao
interface SubscriptionPlanDao {

    // ==========================================
    // CREATE / INSERT
    // ==========================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: SubscriptionPlan): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlans(plans: List<SubscriptionPlan>): List<Long>

    // ==========================================
    // READ / ACCESS CHECKS
    // ==========================================

    @Query("SELECT * FROM subscription_plans WHERE planId = :planId LIMIT 1")
    suspend fun getPlanById(planId: String): SubscriptionPlan?

    @Query("SELECT * FROM subscription_plans WHERE planId = :planId LIMIT 1")
    fun observePlanById(planId: String): Flow<SubscriptionPlan?>

    @Query("SELECT * FROM subscription_plans ORDER BY durationMonths DESC")
    suspend fun getAllPlans(): List<SubscriptionPlan>

    @Query("SELECT * FROM subscription_plans ORDER BY durationMonths DESC")
    fun observeAllPlans(): Flow<List<SubscriptionPlan>>

    @Query("SELECT * FROM subscription_plans WHERE isActive = 1 LIMIT 1")
    suspend fun getActivePlan(): SubscriptionPlan?

    @Query("SELECT * FROM subscription_plans WHERE isActive = 1 LIMIT 1")
    fun observeActivePlan(): Flow<SubscriptionPlan?>

    /**
     * Directly checks if the user currently possesses an active, unexpired subscription.
     */
    @Query("""
        SELECT COUNT(*) > 0 
        FROM subscription_plans 
        WHERE isActive = 1 AND (expiryDate IS NULL OR expiryDate > :currentTime)
    """)
    suspend fun hasValidAccess(currentTime: Long = System.currentTimeMillis()): Boolean

    /**
     * Observes whether the user has active subscription access as a reactive Flow.
     */
    @Query("""
        SELECT COUNT(*) > 0 
        FROM subscription_plans 
        WHERE isActive = 1 AND (expiryDate IS NULL OR expiryDate > :currentTime)
    """)
    fun observeHasValidAccess(currentTime: Long = System.currentTimeMillis()): Flow<Boolean>

    // ==========================================
    // UPDATE & ACTIVATION
    // ==========================================

    @Update
    suspend fun updatePlan(plan: SubscriptionPlan): Int

    @Query("UPDATE subscription_plans SET isActive = 0, updatedAt = :updatedAt")
    suspend fun deactivateAllPlans(updatedAt: Long = System.currentTimeMillis()): Int

    @Query("""
        UPDATE subscription_plans 
        SET isActive = 1, startDate = :startDate, expiryDate = :expiryDate, 
            paymentReferenceId = :paymentId, updatedAt = :updatedAt 
        WHERE planId = :planId
    """)
    suspend fun activatePlanDirect(
        planId: String,
        startDate: Long,
        expiryDate: Long,
        paymentId: String?,
        updatedAt: Long = System.currentTimeMillis()
    ): Int

    /**
     * Atomically deactivates any existing plans and activates the specified plan.
     */
    @Transaction
    suspend fun activateSubscriptionPlan(
        planId: String,
        startDate: Long,
        expiryDate: Long,
        paymentId: String? = null
    ) {
        deactivateAllPlans()
        activatePlanDirect(
            planId = planId,
            startDate = startDate,
            expiryDate = expiryDate,
            paymentId = paymentId
        )
    }

    // ==========================================
    // DELETE
    // ==========================================

    @Delete
    suspend fun deletePlan(plan: SubscriptionPlan): Int

    @Query("DELETE FROM subscription_plans WHERE planId = :planId")
    suspend fun deletePlanById(planId: String): Int

    @Query("DELETE FROM subscription_plans")
    suspend fun deleteAllPlans(): Int
}
