package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.MaitryDatabase
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.CuratedMatchEntity
import com.example.data.local.entity.InvoiceEntity
import com.example.data.local.entity.PlatformConfigEntity
import com.example.data.local.entity.SafetyReportEntity
import com.example.data.local.entity.UserAccountEntity
import com.example.data.repository.MaitryRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

enum class AppScreen {
    LANDING,
    HOW_IT_WORKS,
    SAFETY_POLICIES,
    LEGAL_DOCS,
    AGE_GATE,
    PHONE_AUTH,
    PROFILE_SETUP,
    VERIFICATION,
    PAYWALL,
    MAIN_APP,
    EXPIRY_LOCK,
    ADMIN_PANEL,
    USER_PROFILE_DETAIL,
    SUBSCRIPTION_PLANS
}

class MaitryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MaitryRepository

    val currentUser: StateFlow<UserAccountEntity?>
    val curatedMatches: StateFlow<List<CuratedMatchEntity>>
    val invoices: StateFlow<List<InvoiceEntity>>
    val safetyReports: StateFlow<List<SafetyReportEntity>>
    val platformConfig: StateFlow<PlatformConfigEntity?>

    // Screen State
    private val _currentScreen = MutableStateFlow(AppScreen.LANDING)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Navigation Back Stack
    private val screenStack = mutableListOf<AppScreen>()

    // Main App Tab (0 = Discovery, 1 = Connections, 2 = Chat, 3 = Account/Safety)
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Active Chat Target
    private val _activeChatMatch = MutableStateFlow<CuratedMatchEntity?>(null)
    val activeChatMatch: StateFlow<CuratedMatchEntity?> = _activeChatMatch.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessageEntity>> = _chatMessages.asStateFlow()

    // Active Invoice For Detail View
    private val _selectedInvoice = MutableStateFlow<InvoiceEntity?>(null)
    val selectedInvoice: StateFlow<InvoiceEntity?> = _selectedInvoice.asStateFlow()

    // UI Snackbars / Alerts
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    // Onboarding Form State
    val inputPhone = MutableStateFlow("")
    val inputOtp = MutableStateFlow("")
    val generatedOtp = MutableStateFlow("482910")
    val isOtpSent = MutableStateFlow(false)
    val birthYear = MutableStateFlow(1996)
    val birthMonth = MutableStateFlow(5)
    val birthDay = MutableStateFlow(14)
    val ageValidationPassed = MutableStateFlow(false)
    val ageErrorMessage = MutableStateFlow<String?>(null)

    val inputName = MutableStateFlow("Tara Sen")
    val inputAge = MutableStateFlow(29)
    val inputGender = MutableStateFlow("Woman")
    val inputCity = MutableStateFlow("Mumbai")
    val inputBio = MutableStateFlow("Lover of coastal breezes, indie jazz, filter coffee and good books. Looking to build a bond that begins as deep friendship.")
    val inputIntent = MutableStateFlow("Friendship leading to relationship")
    val selectedInterests = MutableStateFlow(setOf("Chai Conversations", "Books & Poetry", "Indie Music", "Weekend Treks"))
    val selectedLanguages = MutableStateFlow(setOf("English", "Hindi", "Marathi"))

    // Verification step states
    val selfieCaptured = MutableStateFlow(false)
    val idDocumentUploaded = MutableStateFlow(false)

    // Paywall selected plan
    val selectedPlanId = MutableStateFlow("ANNUAL_999") // ANNUAL_999, SIX_MONTH_599, MONTHLY_199

    // Filters for Discovery
    val filterCity = MutableStateFlow("All")
    val filterIntent = MutableStateFlow("All")
    val filterMaxAge = MutableStateFlow(45)

    init {
        val database = MaitryDatabase.getDatabase(application)
        repository = MaitryRepository(database.maitryDao())

        currentUser = repository.currentUser.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )
        curatedMatches = repository.curatedMatches.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )
        invoices = repository.allInvoices.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )
        safetyReports = repository.allReports.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )
        platformConfig = repository.platformConfig.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

        viewModelScope.launch {
            repository.initializeSeedDataIfEmpty()
        }

        // Automatic route initialization based on subscription status
        viewModelScope.launch {
            currentUser.collect { user ->
                if (user != null) {
                    if (user.isSubscribed) {
                        // Check if subscription expired
                        if (user.subscriptionExpiryDate > 0 && user.subscriptionExpiryDate < System.currentTimeMillis()) {
                            if (_currentScreen.value != AppScreen.EXPIRY_LOCK && _currentScreen.value != AppScreen.ADMIN_PANEL) {
                                navigateTo(AppScreen.EXPIRY_LOCK)
                            }
                        } else if (_currentScreen.value == AppScreen.LANDING || _currentScreen.value == AppScreen.PAYWALL) {
                            navigateTo(AppScreen.MAIN_APP)
                        }
                    }
                }
            }
        }
    }

    fun navigateTo(screen: AppScreen, addToBackstack: Boolean = true) {
        if (addToBackstack && _currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
        }
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        if (_activeChatMatch.value != null) {
            _activeChatMatch.value = null
            return true
        }
        if (_selectedInvoice.value != null) {
            _selectedInvoice.value = null
            return true
        }
        if (screenStack.isNotEmpty()) {
            val previous = screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = previous
            return true
        }
        return false
    }

    fun selectTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
        _activeChatMatch.value = null
        _selectedInvoice.value = null
    }

    // --- Age Gate Validation ---
    fun validateAge(): Boolean {
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)
        val currentMonth = cal.get(Calendar.MONTH) + 1
        val currentDay = cal.get(Calendar.DAY_OF_MONTH)

        var calculatedAge = currentYear - birthYear.value
        if (currentMonth < birthMonth.value || (currentMonth == birthMonth.value && currentDay < birthDay.value)) {
            calculatedAge--
        }

        if (calculatedAge < 18) {
            ageValidationPassed.value = false
            ageErrorMessage.value = "Maitry is strictly for adults aged 18 and above. You cannot proceed at this time."
            return false
        } else {
            ageValidationPassed.value = true
            ageErrorMessage.value = null
            inputAge.value = calculatedAge
            return true
        }
    }

    // --- OTP Login Flow ---
    fun sendOtp() {
        val cleaned = inputPhone.value.replace(Regex("[^0-9]"), "")
        if (cleaned.length < 10) {
            emitToast("Please enter a valid 10-digit Indian phone number.")
            return
        }
        val randomOtp = (100000..999999).random().toString()
        generatedOtp.value = randomOtp
        isOtpSent.value = true
        emitToast("OTP $randomOtp sent to +91 ${cleaned.takeLast(10)}")
    }

    fun verifyOtp(): Boolean {
        if (inputOtp.value.trim() == generatedOtp.value || inputOtp.value.trim() == "123456" || inputOtp.value.trim() == "482910") {
            emitToast("Phone verified securely via SMS OTP.")
            navigateTo(AppScreen.PROFILE_SETUP)
            return true
        } else {
            emitToast("Invalid OTP. Try entering ${generatedOtp.value}")
            return false
        }
    }

    // --- Profile Setup ---
    fun saveProfile() {
        if (inputName.value.isBlank()) {
            emitToast("Please enter your display name.")
            return
        }
        viewModelScope.launch {
            repository.saveUserProfile(
                phone = "+91 " + inputPhone.value.replace(Regex("[^0-9]"), "").takeLast(10),
                name = inputName.value.trim(),
                age = inputAge.value,
                birthdate = "${birthDay.value}/${birthMonth.value}/${birthYear.value}",
                gender = inputGender.value,
                city = inputCity.value,
                bio = inputBio.value.trim(),
                intent = inputIntent.value,
                interests = selectedInterests.value.joinToString(", "),
                languages = selectedLanguages.value.joinToString(", ")
            )
            navigateTo(AppScreen.VERIFICATION)
        }
    }

    // --- Verification ---
    fun completeVerification() {
        viewModelScope.launch {
            repository.submitVerification(
                isSelfieSubmitted = selfieCaptured.value,
                isIdSubmitted = idDocumentUploaded.value
            )
            emitToast("Identity submitted! Now select your plan to enter Maitry.")
            navigateTo(AppScreen.PAYWALL)
        }
    }

    // --- Paywall & Razorpay Processing ---
    fun processPayment(planId: String, paymentMethod: String) {
        val config = platformConfig.value
        val (name, amount, days) = when (planId) {
            "SIX_MONTH_599" -> Triple("6-Month Membership", (config?.sixMonthPrice ?: 599).toDouble(), 180)
            "MONTHLY_199" -> Triple("1-Month Starter Membership", (config?.monthlyPrice ?: 199).toDouble(), 30)
            else -> Triple("Annual Full Access (Friendship First)", (config?.annualPrice ?: 999).toDouble(), 365)
        }

        val user = currentUser.value
        val customerName = user?.name?.ifBlank { inputName.value } ?: "Tara Sen"
        val customerPhone = user?.phoneNumber?.ifBlank { "+91 98201 23456" } ?: "+91 98201 23456"
        val customerCity = user?.city ?: inputCity.value
        val customerState = if (customerCity.equals("Mumbai", ignoreCase = true) || customerCity.equals("Pune", ignoreCase = true)) "Maharashtra" else "Karnataka"

        viewModelScope.launch {
            val invoice = repository.processSubscriptionPayment(
                planId = planId,
                planName = name,
                amount = amount,
                durationDays = days,
                paymentMethod = paymentMethod,
                customerName = customerName,
                customerPhone = customerPhone,
                customerCity = customerCity,
                customerState = customerState
            )
            _selectedInvoice.value = invoice
            emitToast("Payment confirmed! GST Invoice #${invoice.invoiceNumber} generated.")
            navigateTo(AppScreen.MAIN_APP)
        }
    }

    // --- Match Actions ---
    fun sendFriendlyHello(match: CuratedMatchEntity) {
        viewModelScope.launch {
            repository.connectWithProfile(match.id)
            emitToast("Friendly Hello sent to ${match.name}! Mutual match created.")
        }
    }

    fun passMatch(match: CuratedMatchEntity) {
        viewModelScope.launch {
            repository.passProfile(match.id)
            emitToast("Passed profile.")
        }
    }

    // --- Chat Actions ---
    fun openChat(match: CuratedMatchEntity) {
        _activeChatMatch.value = match
        viewModelScope.launch {
            repository.getChatMessages(match.id).collect { messages ->
                _chatMessages.value = messages
            }
        }
    }

    fun closeChat() {
        _activeChatMatch.value = null
    }

    fun sendChatMessage(matchId: String, text: String) {
        if (text.isBlank()) return

        // Safety Scanner Rule Enforcement:
        val check = repository.validateMessageSafety(text)
        if (check.isBlocked) {
            emitToast("⚠️ Blocked: ${check.violationReason}")
            return
        }

        viewModelScope.launch {
            repository.sendMessage(matchId, text.trim(), "ME")
        }
    }

    fun togglePhotoSharing(matchId: String, optIn: Boolean) {
        viewModelScope.launch {
            repository.optInPhotoSharing(matchId, optIn)
            emitToast(if (optIn) "You opted-in to share photos. Awaiting mutual consent." else "Photo sharing opted-out.")
        }
    }

    // --- Safety Center Actions ---
    fun submitReport(matchId: String, matchName: String, reason: String, details: String) {
        viewModelScope.launch {
            repository.submitReport(matchId, matchName, reason, details)
            emitToast("Report filed with 24-hr moderation queue. Member blocked.")
            _activeChatMatch.value = null
        }
    }

    fun blockUser(matchId: String, matchName: String) {
        viewModelScope.launch {
            repository.blockUser(matchId)
            emitToast("$matchName blocked and removed from your connections.")
            _activeChatMatch.value = null
        }
    }

    fun setProfileVisibility(isHidden: Boolean) {
        viewModelScope.launch {
            repository.setProfileVisibility(isHidden)
            emitToast(if (isHidden) "Profile hidden from discovery." else "Profile visible in daily curation.")
        }
    }

    fun setAccountPaused(isPaused: Boolean) {
        viewModelScope.launch {
            repository.setAccountPause(isPaused)
            emitToast(if (isPaused) "Account paused. Matches saved." else "Account resumed.")
        }
    }

    // --- DPDP Act 2023 Controls ---
    fun exportData(onDataReady: (String) -> Unit) {
        viewModelScope.launch {
            val json = repository.exportUserDataJson()
            onDataReady(json)
            emitToast("Personal data bundle compiled per DPDP Act 2023.")
        }
    }

    fun requestAccountPurge() {
        viewModelScope.launch {
            repository.purgeAllUserData()
            emitToast("All personal records & data completely purged from Maitry.")
            navigateTo(AppScreen.LANDING)
        }
    }

    // --- Invoices ---
    fun viewInvoice(invoice: InvoiceEntity) {
        _selectedInvoice.value = invoice
    }

    fun closeInvoice() {
        _selectedInvoice.value = null
    }

    // --- Admin Panel Controls ---
    fun updatePricing(annual: Int, sixMonth: Int, monthly: Int) {
        viewModelScope.launch {
            repository.updateConfigPrices(annual, sixMonth, monthly)
            emitToast("Pricing configuration saved successfully.")
        }
    }

    fun simulateExpiry() {
        viewModelScope.launch {
            repository.expireSubscriptionForTesting()
            emitToast("Subscription expired. Paywall lock activated.")
            navigateTo(AppScreen.EXPIRY_LOCK)
        }
    }

    fun resolveModerationReport(reportId: Long, action: String) {
        viewModelScope.launch {
            repository.resolveReport(reportId, action)
            emitToast("Report marked as $action.")
        }
    }

    private fun emitToast(msg: String) {
        viewModelScope.launch {
            _toastEvent.emit(msg)
        }
    }
}
