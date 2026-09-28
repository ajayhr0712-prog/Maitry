package com.example.data.repository

import com.example.data.local.dao.UserProfileDao
import com.example.data.local.entity.UserProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * UserProfileRepository mediates between the UI/ViewModel layer and the Room database DAO,
 * providing a clean, thread-safe, and decoupled API for user profile persistence and management.
 */
class UserProfileRepository(
    private val profileDao: UserProfileDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * Reactive stream observing the primary user's profile (defaults to ID 1L).
     */
    val activeUserProfile: Flow<UserProfile?> = profileDao.observeProfileById(1L)
        .flowOn(ioDispatcher)

    /**
     * Reactive stream observing all locally saved user profiles.
     */
    val allUserProfiles: Flow<List<UserProfile>> = profileDao.observeAllProfiles()
        .flowOn(ioDispatcher)

    /**
     * Observes a specific profile by ID.
     */
    fun observeProfileById(id: Long): Flow<UserProfile?> {
        return profileDao.observeProfileById(id).flowOn(ioDispatcher)
    }

    /**
     * Observes profiles filtered by subscription status (e.g. "ACTIVE", "EXPIRED", "INACTIVE").
     */
    fun observeProfilesBySubscription(status: String): Flow<List<UserProfile>> {
        return profileDao.observeProfilesBySubscriptionStatus(status).flowOn(ioDispatcher)
    }

    /**
     * Fetches a user profile synchronously on the IO dispatcher.
     */
    suspend fun getProfile(id: Long = 1L): UserProfile? = withContext(ioDispatcher) {
        profileDao.getProfileById(id)
    }

    /**
     * Saves or replaces a complete [UserProfile].
     */
    suspend fun saveProfile(profile: UserProfile): Long = withContext(ioDispatcher) {
        val updatedProfile = profile.copy(updatedAt = System.currentTimeMillis())
        profileDao.insertProfile(updatedProfile)
    }

    /**
     * Saves or updates profile details (name, age, city, bio, interests).
     * If the profile exists, preserves the current subscription status.
     */
    suspend fun saveProfileDetails(
        name: String,
        age: Int,
        city: String,
        bio: String,
        interests: String,
        photoUrls: String? = null,
        id: Long = 1L
    ): UserProfile = withContext(ioDispatcher) {
        val existing = profileDao.getProfileById(id)
        val target = UserProfile(
            id = id,
            name = name.trim(),
            age = age,
            city = city.trim(),
            bio = bio.trim(),
            interests = interests.trim(),
            subscriptionStatus = existing?.subscriptionStatus ?: "INACTIVE",
            isSubscribed = existing?.isSubscribed ?: false,
            photoUrls = photoUrls ?: existing?.photoUrls ?: "",
            updatedAt = System.currentTimeMillis()
        )
        profileDao.insertProfile(target)
        target
    }

    /**
     * Updates subscription status and active flag for the given user profile.
     */
    suspend fun updateSubscriptionStatus(
        status: String,
        isSubscribed: Boolean,
        id: Long = 1L
    ): UserProfile? = withContext(ioDispatcher) {
        val now = System.currentTimeMillis()
        profileDao.updateSubscriptionStatus(
            id = id,
            status = status,
            isSubscribed = isSubscribed,
            updatedAt = now
        )
        profileDao.getProfileById(id)
    }

    /**
     * Deletes a user profile by ID.
     */
    suspend fun deleteProfile(id: Long = 1L): Boolean = withContext(ioDispatcher) {
        val rowsDeleted = profileDao.deleteProfileById(id)
        rowsDeleted > 0
    }

    /**
     * Purges all user profile data from the database.
     */
    suspend fun clearAllProfiles(): Int = withContext(ioDispatcher) {
        profileDao.deleteAllProfiles()
    }

    /**
     * Returns the total count of user profiles in local storage.
     */
    suspend fun getProfileCount(): Int = withContext(ioDispatcher) {
        profileDao.getProfileCount()
    }
}
