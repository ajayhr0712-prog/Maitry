package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.MaitryDatabase
import com.example.data.repository.MaitryRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `verify app name resource is Maitry`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Maitry", appName)
    }

    @Test
    fun `verify chat safety filter blocks 10-digit phone numbers`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = MaitryDatabase.getDatabase(context)
        val repository = MaitryRepository(database.maitryDao())

        val unsafeMessage1 = "Hey, call me on 9820123456"
        val result1 = repository.validateMessageSafety(unsafeMessage1)
        assertTrue("Phone number should be blocked", result1.isBlocked)

        val unsafeMessage2 = "Text me on +91 98765 43210"
        val result2 = repository.validateMessageSafety(unsafeMessage2)
        assertTrue("Formatted phone number should be blocked", result2.isBlocked)

        val safeMessage = "I love exploring old bookstores in Bengaluru and drinking filter coffee."
        val safeResult = repository.validateMessageSafety(safeMessage)
        assertFalse("Safe message should pass", safeResult.isBlocked)
    }

    @Test
    fun `verify chat safety filter blocks links and messengers`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = MaitryDatabase.getDatabase(context)
        val repository = MaitryRepository(database.maitryDao())

        val linkMessage = "Check out my profile at https://t.me/randomuser"
        val result = repository.validateMessageSafety(linkMessage)
        assertTrue("Telegram link should be blocked", result.isBlocked)

        val instaMessage = "Add me on insta or wa.me/12345"
        val result2 = repository.validateMessageSafety(instaMessage)
        assertTrue("External messenger handle should be blocked", result2.isBlocked)
    }

    @Test
    fun `verify GST 18 percent calculation for Annual 999 plan`() {
        val total = 999.0
        val base = Math.round((total / 1.18) * 100.0) / 100.0
        val gst = Math.round((total - base) * 100.0) / 100.0

        assertEquals(846.61, base, 0.01)
        assertEquals(152.39, gst, 0.01)
        assertEquals(999.00, base + gst, 0.01)
    }

    @Test
    fun `verify LegalPolicies contains all mandatory drafts marked Draft for legal review`() {
        val policies = LegalPolicies.allPolicies
        assertEquals(4, policies.size)

        policies.forEach { doc ->
            assertEquals("Draft for legal review", doc.statusBadge)
            assertTrue("Header must indicate Draft for legal review", doc.header.contains("DRAFT FOR LEGAL REVIEW"))
            assertTrue("Preamble must indicate draft for legal review", doc.preamble.contains("DRAFT FOR LEGAL REVIEW", ignoreCase = true))
        }

        val tos = LegalPolicies.termsOfService
        assertTrue(tos.statutoryBasis.contains("Information Technology Act"))
        assertTrue(tos.sections.any { it.title.contains("18+") })

        val privacy = LegalPolicies.privacyPolicy
        assertTrue(privacy.statutoryBasis.contains("DPDP Act"))
        assertTrue(privacy.sections.any { it.title.contains("Data Fiduciary") })

        val refund = LegalPolicies.refundPolicy
        assertTrue(refund.sections.any { it.title.contains("Auto-Renewal") })

        val guidelines = LegalPolicies.communityGuidelines
        assertTrue(guidelines.sections.any { it.title.contains("Anti-Sextortion") || it.title.contains("Financial Solicitation") })
    }

    @Test
    fun `verify UserProfile Room entity persistence and fields`() = kotlinx.coroutines.test.runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = MaitryDatabase.getDatabase(context)
        val dao = database.maitryDao()

        val sampleProfile = com.example.data.local.entity.UserProfile(
            id = 1L,
            name = "Aarav Sharma",
            age = 29,
            city = "Bengaluru",
            bio = "Architect passionate about sustainable urban spaces and indie cinema.",
            interests = "Architecture, Filter Coffee, Books, Ghats Cycling",
            subscriptionStatus = "ACTIVE",
            isSubscribed = true
        )

        dao.insertOrUpdateUserProfile(sampleProfile)

        val retrieved = dao.getUserProfileSync(1L)
        org.junit.Assert.assertNotNull(retrieved)
        assertEquals("Aarav Sharma", retrieved?.name)
        assertEquals(29, retrieved?.age)
        assertEquals("Bengaluru", retrieved?.city)
        assertEquals("Architect passionate about sustainable urban spaces and indie cinema.", retrieved?.bio)
        assertEquals("Architecture, Filter Coffee, Books, Ghats Cycling", retrieved?.interests)
        assertEquals("ACTIVE", retrieved?.subscriptionStatus)
        assertTrue(retrieved?.isSubscribed == true)
    }

    @Test
    fun `verify UserProfileDao CRUD operations`() = kotlinx.coroutines.test.runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = MaitryDatabase.getDatabase(context)
        val profileDao = database.userProfileDao()

        // 1. Clean slate
        profileDao.deleteAllProfiles()
        assertEquals(0, profileDao.getProfileCount())

        // 2. CREATE (Insert)
        val profile = com.example.data.local.entity.UserProfile(
            id = 101L,
            name = "Meera Iyer",
            age = 27,
            city = "Chennai",
            bio = "Carnatic vocalist and UI designer.",
            interests = "Classical Music, Typographic Art, Filter Coffee",
            subscriptionStatus = "INACTIVE",
            isSubscribed = false
        )
        val rowId = profileDao.insertProfile(profile)
        assertTrue(rowId >= 0)
        assertEquals(1, profileDao.getProfileCount())

        // 3. READ (Query by ID and List)
        val fetched = profileDao.getProfileById(101L)
        org.junit.Assert.assertNotNull(fetched)
        assertEquals("Meera Iyer", fetched?.name)
        assertEquals("Chennai", fetched?.city)
        assertEquals("INACTIVE", fetched?.subscriptionStatus)
        assertFalse(fetched?.isSubscribed == true)

        val all = profileDao.getAllProfiles()
        assertEquals(1, all.size)

        // 4. UPDATE (Modify fields and status)
        val updatedRows = profileDao.updateSubscriptionStatus(
            id = 101L,
            status = "ACTIVE",
            isSubscribed = true
        )
        assertEquals(1, updatedRows)

        val updatedDetails = profileDao.updateProfileDetails(
            id = 101L,
            name = "Meera R. Iyer",
            age = 28,
            city = "Bengaluru",
            bio = "Senior Product Designer & Vocalist.",
            interests = "Classical Music, Coffee, Design"
        )
        assertEquals(1, updatedDetails)

        val refetched = profileDao.getProfileById(101L)
        assertEquals("Meera R. Iyer", refetched?.name)
        assertEquals(28, refetched?.age)
        assertEquals("Bengaluru", refetched?.city)
        assertEquals("ACTIVE", refetched?.subscriptionStatus)
        assertTrue(refetched?.isSubscribed == true)

        // 5. DELETE
        val deletedCount = profileDao.deleteProfileById(101L)
        assertEquals(1, deletedCount)
        assertEquals(0, profileDao.getProfileCount())
        org.junit.Assert.assertNull(profileDao.getProfileById(101L))
    }

    @Test
    fun `verify AppDatabase instantiates and provides functional UserProfileDao`() = kotlinx.coroutines.test.runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appDb = com.example.data.local.AppDatabase.getDatabase(context)
        val userProfileDao = appDb.userProfileDao()

        // Clean
        userProfileDao.deleteAllProfiles()

        // Insert using AppDatabase
        val profile = com.example.data.local.entity.UserProfile(
            id = 202L,
            name = "Rohan Verma",
            age = 31,
            city = "Pune",
            bio = "Tech founder exploring heritage walks and filter coffee.",
            interests = "History, Running, Podcasts",
            subscriptionStatus = "ACTIVE",
            isSubscribed = true
        )
        userProfileDao.insertProfile(profile)

        val retrieved = userProfileDao.getProfileById(202L)
        org.junit.Assert.assertNotNull(retrieved)
        assertEquals("Rohan Verma", retrieved?.name)
        assertEquals(31, retrieved?.age)
        assertEquals("Pune", retrieved?.city)
        assertEquals("ACTIVE", retrieved?.subscriptionStatus)
        assertTrue(retrieved?.isSubscribed == true)

        userProfileDao.deleteProfileById(202L)
        org.junit.Assert.assertNull(userProfileDao.getProfileById(202L))
    }

    @Test
    fun `verify UserProfileViewModel form saving and subscription management`() = kotlinx.coroutines.test.runTest {
        val application = ApplicationProvider.getApplicationContext<android.app.Application>()
        val appDb = com.example.data.local.AppDatabase.getDatabase(application)
        val profileDao = appDb.userProfileDao()
        profileDao.deleteAllProfiles()

        val viewModel = com.example.viewmodel.UserProfileViewModel(application, profileDao)

        // Set form inputs
        viewModel.onNameChange("Priya Deshmukh")
        viewModel.onAgeChange("26")
        viewModel.onCityChange("Mumbai")
        viewModel.onBioChange("Passionate about heritage architecture and pottery.")
        viewModel.onInterestsChange("Pottery, Art Galleries, Marine Drive Walks")

        // Save
        val savedProfile = viewModel.saveFormProfileSync(id = 1L)
        org.junit.Assert.assertNotNull(savedProfile)

        // Verify direct from DAO
        val saved = profileDao.getProfileById(1L)
        org.junit.Assert.assertNotNull(saved)
        assertEquals("Priya Deshmukh", saved?.name)
        assertEquals(26, saved?.age)
        assertEquals("Mumbai", saved?.city)
        assertEquals("Passionate about heritage architecture and pottery.", saved?.bio)

        // Update subscription status via ViewModel
        viewModel.updateSubscriptionStatusSync(status = "ACTIVE", isSubscribed = true, id = 1L)

        val updated = profileDao.getProfileById(1L)
        assertEquals("ACTIVE", updated?.subscriptionStatus)
        assertTrue(updated?.isSubscribed == true)

        // Delete profile via ViewModel
        viewModel.deleteProfileSync(id = 1L)
        val afterDelete = profileDao.getProfileById(1L)
        org.junit.Assert.assertNull(afterDelete)
    }

    @Test
    fun `verify UserProfileRepository mediates between ViewModel and DAO`() = kotlinx.coroutines.test.runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appDb = com.example.data.local.AppDatabase.getDatabase(context)
        val profileDao = appDb.userProfileDao()
        val repository = com.example.data.repository.UserProfileRepository(
            profileDao = profileDao,
            ioDispatcher = kotlinx.coroutines.Dispatchers.Unconfined
        )

        repository.clearAllProfiles()
        assertEquals(0, repository.getProfileCount())

        // Save via repository
        val created = repository.saveProfileDetails(
            name = "Kavita Nair",
            age = 30,
            city = "Kochi",
            bio = "Marine biologist and sunset enthusiast.",
            interests = "Diving, Oceanography, Carnatic Flute",
            id = 505L
        )
        assertEquals("Kavita Nair", created.name)
        assertEquals(30, created.age)
        assertEquals("Kochi", created.city)
        assertEquals(1, repository.getProfileCount())

        // Update subscription via repository
        val updated = repository.updateSubscriptionStatus(
            status = "ACTIVE",
            isSubscribed = true,
            id = 505L
        )
        assertEquals("ACTIVE", updated?.subscriptionStatus)
        assertTrue(updated?.isSubscribed == true)

        // Read through repository
        val retrieved = repository.getProfile(505L)
        org.junit.Assert.assertNotNull(retrieved)
        assertEquals("Kavita Nair", retrieved?.name)

        // Delete through repository
        val deleted = repository.deleteProfile(505L)
        assertTrue(deleted)
        assertEquals(0, repository.getProfileCount())
        org.junit.Assert.assertNull(repository.getProfile(505L))
    }

    @Test
    fun `verify SubscriptionPlan and SubscriptionPlanDao track annual and 6-month access`() = kotlinx.coroutines.test.runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = com.example.data.local.AppDatabase.getDatabase(context)
        val planDao = database.subscriptionPlanDao()

        // 1. Initial cleanup
        planDao.deleteAllPlans()
        assertFalse(planDao.hasValidAccess())

        // 2. Insert Annual (12-month) and 6-Month plans
        val annualPlan = com.example.data.local.entity.SubscriptionPlan(
            planId = com.example.data.local.entity.SubscriptionPlan.ID_ANNUAL,
            title = "12 Months Full Membership",
            durationMonths = 12,
            priceInr = 2999.0,
            isActive = false
        )
        val sixMonthPlan = com.example.data.local.entity.SubscriptionPlan(
            planId = com.example.data.local.entity.SubscriptionPlan.ID_6_MONTHS,
            title = "6 Months Standard Access",
            durationMonths = 6,
            priceInr = 1799.0,
            isActive = false
        )

        planDao.insertPlans(listOf(annualPlan, sixMonthPlan))
        val allPlans = planDao.getAllPlans()
        assertEquals(2, allPlans.size)

        // Without activation, access to core content must be denied
        assertFalse(planDao.hasValidAccess())

        // 3. Activate 6-Month Plan
        val now = System.currentTimeMillis()
        val sixMonthsExpiry = now + (180L * 24L * 60L * 60L * 1000L)
        planDao.activateSubscriptionPlan(
            planId = com.example.data.local.entity.SubscriptionPlan.ID_6_MONTHS,
            startDate = now,
            expiryDate = sixMonthsExpiry,
            paymentId = "pay_mock_razorpay_6m"
        )

        // Verify active plan and access granted
        val active6m = planDao.getActivePlan()
        org.junit.Assert.assertNotNull(active6m)
        assertEquals(com.example.data.local.entity.SubscriptionPlan.ID_6_MONTHS, active6m?.planId)
        assertEquals(6, active6m?.durationMonths)
        assertTrue(active6m?.isActive == true)
        assertTrue(planDao.hasValidAccess())

        // 4. Upgrade to Annual Plan (atomically deactivates 6-month plan)
        val oneYearExpiry = now + (365L * 24L * 60L * 60L * 1000L)
        planDao.activateSubscriptionPlan(
            planId = com.example.data.local.entity.SubscriptionPlan.ID_ANNUAL,
            startDate = now,
            expiryDate = oneYearExpiry,
            paymentId = "pay_mock_razorpay_annual"
        )

        val activeAnnual = planDao.getActivePlan()
        org.junit.Assert.assertNotNull(activeAnnual)
        assertEquals(com.example.data.local.entity.SubscriptionPlan.ID_ANNUAL, activeAnnual?.planId)
        assertEquals(12, activeAnnual?.durationMonths)
        assertTrue(activeAnnual?.isActive == true)

        val previous6m = planDao.getPlanById(com.example.data.local.entity.SubscriptionPlan.ID_6_MONTHS)
        assertFalse(previous6m?.isActive == true)
        assertTrue(planDao.hasValidAccess())

        // 5. Expiration check: if expiry date is in the past, access check must fail
        val expiredTime = now - 10000L
        val pastAccess = planDao.hasValidAccess(currentTime = oneYearExpiry + 1000L)
        assertFalse(pastAccess)

        // 6. Test with SubscriptionRepository wrapper
        val repository = com.example.data.repository.SubscriptionRepository(
            planDao = planDao,
            ioDispatcher = kotlinx.coroutines.Dispatchers.Unconfined
        )
        assertTrue(repository.checkUserAccess(currentTime = now))
        repository.expireSubscription()
        assertFalse(repository.checkUserAccess(currentTime = now))
    }
}
