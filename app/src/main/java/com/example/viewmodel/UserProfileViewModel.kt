package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.dao.UserProfileDao
import com.example.data.local.entity.UserProfile
import com.example.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * UI State encapsulating the user profile data, loading states, and feedback messages.
 */
data class UserProfileUiState(
    val profile: UserProfile? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

/**
 * One-shot UI events for navigation, notifications, or snackbars.
 */
sealed class UserProfileEvent {
    data class ShowMessage(val message: String) : UserProfileEvent()
    data class ProfileUpdated(val profile: UserProfile) : UserProfileEvent()
    data class SubscriptionChanged(val status: String, val isSubscribed: Boolean) : UserProfileEvent()
    data class ProfileDeleted(val id: Long) : UserProfileEvent()
}

/**
 * UserProfileViewModel interacts with [UserProfileRepository] to fetch, update,
 * and manage user data locally across all app screens.
 */
class UserProfileViewModel(
    application: Application,
    private val repository: UserProfileRepository = UserProfileRepository(
        AppDatabase.getDatabase(application).userProfileDao()
    )
) : AndroidViewModel(application) {

    /**
     * Secondary constructor supporting direct DAO injection for flexibility in tests.
     */
    constructor(application: Application, profileDao: UserProfileDao) : this(
        application = application,
        repository = UserProfileRepository(profileDao)
    )

    // Reactive profile observation for active profile (ID = 1L)
    val userProfile: StateFlow<UserProfile?> = repository.activeUserProfile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // Reactive stream of all profiles stored locally
    val allProfiles: StateFlow<List<UserProfile>> = repository.allUserProfiles
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // UI state holder
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    // One-shot events
    private val _events = MutableSharedFlow<UserProfileEvent>()
    val events: SharedFlow<UserProfileEvent> = _events.asSharedFlow()

    // Editable form state for profile creation and editing
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age.asStateFlow()

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio.asStateFlow()

    private val _interests = MutableStateFlow("")
    val interests: StateFlow<String> = _interests.asStateFlow()

    private val _photoUrls = MutableStateFlow("")
    val photoUrls: StateFlow<String> = _photoUrls.asStateFlow()

    init {
        // Collect current profile to pre-fill form fields whenever the profile changes
        viewModelScope.launch {
            userProfile.collect { current ->
                _uiState.value = _uiState.value.copy(profile = current)
                if (current != null) {
                    if (_name.value.isEmpty()) _name.value = current.name
                    if (_age.value.isEmpty() && current.age > 0) _age.value = current.age.toString()
                    if (_city.value.isEmpty()) _city.value = current.city
                    if (_bio.value.isEmpty()) _bio.value = current.bio
                    if (_interests.value.isEmpty()) _interests.value = current.interests
                    if (_photoUrls.value.isEmpty()) _photoUrls.value = current.photoUrls
                }
            }
        }
    }

    // Form input mutators
    fun onNameChange(value: String) { _name.value = value }
    fun onAgeChange(value: String) { _age.value = value.filter { it.isDigit() } }
    fun onCityChange(value: String) { _city.value = value }
    fun onBioChange(value: String) { _bio.value = value }
    fun onInterestsChange(value: String) { _interests.value = value }
    fun onPhotosChange(value: String) { _photoUrls.value = value }

    fun clearFeedback() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }

    /**
     * Saves or replaces a complete UserProfile object via repository.
     */
    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
                repository.saveProfile(profile)
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    profile = profile,
                    successMessage = "Profile saved successfully"
                )
                _events.emit(UserProfileEvent.ProfileUpdated(profile))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = e.localizedMessage ?: "Failed to save profile"
                )
                _events.emit(UserProfileEvent.ShowMessage("Failed to save profile: ${e.message}"))
            }
        }
    }

    /**
     * Saves the current draft form into the UserProfile entity asynchronously.
     */
    fun saveFormProfile(id: Long = 1L) {
        viewModelScope.launch {
            saveFormProfileSync(id)
        }
    }

    /**
     * Suspend version of saveFormProfile for sequential coroutine execution.
     */
    suspend fun saveFormProfileSync(id: Long = 1L): UserProfile? {
        val parsedAge = _age.value.toIntOrNull() ?: 0
        if (_name.value.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Please enter your name")
            return null
        }
        if (parsedAge < 18) {
            _uiState.value = _uiState.value.copy(errorMessage = "Age must be at least 18 years")
            return null
        }

        return try {
            _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
            val updatedProfile = repository.saveProfileDetails(
                name = _name.value.trim(),
                age = parsedAge,
                city = _city.value.trim(),
                bio = _bio.value.trim(),
                interests = _interests.value.trim(),
                photoUrls = _photoUrls.value.trim().ifEmpty { null },
                id = id
            )
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                profile = updatedProfile,
                successMessage = "Profile details updated"
            )
            _events.emit(UserProfileEvent.ProfileUpdated(updatedProfile))
            updatedProfile
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                errorMessage = e.localizedMessage ?: "Failed to update profile"
            )
            _events.emit(UserProfileEvent.ShowMessage("Update failed: ${e.message}"))
            null
        }
    }

    /**
     * Updates specific profile fields directly via repository.
     */
    fun updateProfileDetails(
        name: String,
        age: Int,
        city: String,
        bio: String,
        interests: String,
        id: Long = 1L
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
                val updated = repository.saveProfileDetails(
                    id = id,
                    name = name,
                    age = age,
                    city = city,
                    bio = bio,
                    interests = interests
                )
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    profile = updated,
                    successMessage = "Profile updated"
                )
                _events.emit(UserProfileEvent.ProfileUpdated(updated))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = e.localizedMessage ?: "Failed to update profile details"
                )
            }
        }
    }

    /**
     * Updates the user's subscription status and active flag asynchronously.
     */
    fun updateSubscriptionStatus(status: String, isSubscribed: Boolean, id: Long = 1L) {
        viewModelScope.launch {
            updateSubscriptionStatusSync(status, isSubscribed, id)
        }
    }

    /**
     * Suspend version of updateSubscriptionStatus via repository.
     */
    suspend fun updateSubscriptionStatusSync(status: String, isSubscribed: Boolean, id: Long = 1L): UserProfile? {
        return try {
            val updated = repository.updateSubscriptionStatus(status = status, isSubscribed = isSubscribed, id = id)
            _uiState.value = _uiState.value.copy(
                profile = updated,
                successMessage = "Subscription status updated to $status"
            )
            _events.emit(UserProfileEvent.SubscriptionChanged(status, isSubscribed))
            updated
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.localizedMessage)
            null
        }
    }

    /**
     * Deletes a profile by ID from local storage asynchronously.
     */
    fun deleteProfile(id: Long = 1L) {
        viewModelScope.launch {
            deleteProfileSync(id)
        }
    }

    /**
     * Suspend version of deleteProfile via repository.
     */
    suspend fun deleteProfileSync(id: Long = 1L): Boolean {
        return try {
            val success = repository.deleteProfile(id)
            if (success) {
                _uiState.value = _uiState.value.copy(
                    profile = null,
                    successMessage = "Profile deleted successfully"
                )
                _name.value = ""
                _age.value = ""
                _city.value = ""
                _bio.value = ""
                _interests.value = ""
                _events.emit(UserProfileEvent.ProfileDeleted(id))
            }
            success
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.localizedMessage)
            false
        }
    }

    /**
     * Purges all profiles (statutory data wipe under DPDP Act 2023) via repository.
     */
    fun clearAllProfiles() {
        viewModelScope.launch {
            try {
                repository.clearAllProfiles()
                _uiState.value = UserProfileUiState(successMessage = "All profile data purged")
                _name.value = ""
                _age.value = ""
                _city.value = ""
                _bio.value = ""
                _interests.value = ""
                _events.emit(UserProfileEvent.ShowMessage("All local profile data erased"))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.localizedMessage)
            }
        }
    }

    /**
     * Synchronously fetches the current profile by ID for immediate checking.
     */
    suspend fun getProfileSync(id: Long = 1L): UserProfile? {
        return repository.getProfile(id)
    }
}
