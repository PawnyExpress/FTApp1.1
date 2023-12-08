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

class ExpenseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val incExpRepository: IncExpRepository,
) : ViewModel() {
    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseDetailsDestination.expenseIdArg])

    val uiState: StateFlow<ExpenseDetailsUiState> =
        incExpRepository.getIncExpStream(expenseId)
            .filterNotNull()
            .map {
                ExpenseDetailsUiState(expenseDetails = it.toExpenseDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExpenseDetailsUiState()
            )

    suspend fun deleteExpense() {
        incExpRepository.deleteExpense(uiState.value.expenseDetails.toIncExp())
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
    data class ExpenseDetailsUiState(
        val expenseDetails: ExpenseDetails = ExpenseDetails()
    )
