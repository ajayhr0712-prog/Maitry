package com.example.data.repository

import com.example.data.local.dao.SubscriptionPlanDao
import com.example.data.local.entity.SubscriptionPlan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Repository mediating between the UI/ViewModel layer and [SubscriptionPlanDao]
 * to store, track, and verify annual and 6-month subscription statuses before
 * granting user access to core content.
 */
class SubscriptionRepository(
    private val planDao: SubscriptionPlanDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * Reactive stream of all available subscription plans.
     */
    val allPlans: Flow<List<SubscriptionPlan>> = planDao.observeAllPlans()
        .flowOn(ioDispatcher)

    /**
     * Reactive stream of the currently activated subscription plan.
     */
    val activePlan: Flow<SubscriptionPlan?> = planDao.observeActivePlan()
        .flowOn(ioDispatcher)

    /**
     * Reactive flow indicating whether the user currently possesses unexpired core content access.
     */
    val hasValidAccessFlow: Flow<Boolean> = planDao.observeHasValidAccess()
        .flowOn(ioDispatcher)

    /**
     * Synchronous check on the IO dispatcher to verify if user access is granted
     * before navigating to or rendering core content (matches, chats, profile details).
     */
    suspend fun checkUserAccess(currentTime: Long = System.currentTimeMillis()): Boolean = withContext(ioDispatcher) {
        planDao.hasValidAccess(currentTime)
    }

    /**
     * Seeds default annual (12-month) and 6-month plans if not already present.
     */
    suspend fun ensureDefaultPlans() = withContext(ioDispatcher) {
        val existing = planDao.getAllPlans()
        if (existing.isEmpty()) {
            planDao.insertPlans(SubscriptionPlan.createDefaultPlans())
        }
    }

    /**
     * Activates an annual or 6-month subscription upon payment confirmation.
     */
    suspend fun activatePlan(
        planId: String,
        durationMonths: Int,
        paymentId: String? = null
    ) = withContext(ioDispatcher) {
        val now = System.currentTimeMillis()
        val durationDays = if (durationMonths == 12) 365L else (durationMonths * 30L)
        val expiryTime = now + (durationDays * 24L * 60L * 60L * 1000L)

        // Ensure plan exists in DB first
        val existing = planDao.getPlanById(planId)
        if (existing == null) {
            val title = if (durationMonths == 12) "Annual Membership" else "6-Month Membership"
            val price = if (durationMonths == 12) 2999.0 else 1799.0
            planDao.insertPlan(
                SubscriptionPlan(
                    planId = planId,
                    title = title,
                    durationMonths = durationMonths,
                    priceInr = price,
                    isActive = false
                )
            )
        }

        planDao.activateSubscriptionPlan(
            planId = planId,
            startDate = now,
            expiryDate = expiryTime,
            paymentId = paymentId
        )
    }

    /**
     * Deactivates all plans (e.g. testing expiration or non-renewal).
     */
    suspend fun expireSubscription() = withContext(ioDispatcher) {
        planDao.deactivateAllPlans()
    }

    /**
     * Retrieves a plan by ID.
     */
    suspend fun getPlan(planId: String): SubscriptionPlan? = withContext(ioDispatcher) {
        planDao.getPlanById(planId)
    }
}
