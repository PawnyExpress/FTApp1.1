package com.example.ftapp11.ui.incexp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ftapp11.data.IncExpRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class IncomeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val incExpRepository: IncExpRepository,
) : ViewModel() {
    private val incomeId : Int = checkNotNull(savedStateHandle[IncomeDetailsDestination.incomeIdArg])

    val uiState: StateFlow<IncomeDetailsUiState> =
        incExpRepository.getIncExpStream(incomeId)
            .filterNotNull()
            .map {
                IncomeDetailsUiState( incomeDetails = it.toIncomeDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = IncomeDetailsUiState()
            )

    suspend fun deleteIncome() {
        incExpRepository.deleteIncome(uiState.value.incomeDetails.toIncExp())
    }
    fun getIncomeId(): Int {

        return incomeId
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IncomeDetailsUiState(
    val incomeDetails: IncomeDetails = IncomeDetails()
)