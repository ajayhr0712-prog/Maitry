package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entity.SubscriptionPlan
import com.example.data.repository.SubscriptionRepository
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
 * ViewModel responsible for fetching and presenting subscription plans (annual and 6-month)
 * using [SubscriptionPlan] and [SubscriptionRepository].
 */
class SubscriptionPlansViewModel(
    application: Application,
    private val repository: SubscriptionRepository = SubscriptionRepository(
        AppDatabase.getDatabase(application).subscriptionPlanDao()
    )
) : AndroidViewModel(application) {

    val availablePlans: StateFlow<List<SubscriptionPlan>> = repository.allPlans
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SubscriptionPlan.createDefaultPlans()
        )

    val activePlan: StateFlow<SubscriptionPlan?> = repository.activePlan
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.ensureDefaultPlans()
        }
    }

    fun selectAndActivatePlan(plan: SubscriptionPlan, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            _isProcessing.value = true
            try {
                repository.activatePlan(
                    planId = plan.planId,
                    durationMonths = plan.durationMonths,
                    paymentId = "pay_razor_${System.currentTimeMillis()}"
                )
                _events.emit("Activated ${plan.title} successfully!")
                onComplete()
            } catch (e: Exception) {
                _events.emit("Activation failed: ${e.message}")
            } finally {
                _isProcessing.value = false
            }
        }
    }
}
