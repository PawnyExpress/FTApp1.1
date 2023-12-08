package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ftapp11.data.IncExpRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an item from the [IncExpRepository]'s data source.
 */
class ExpenseEditViewModel (
    savedStateHandle: SavedStateHandle,
    private val incExpRepository: IncExpRepository
) : ViewModel() {
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseEditDestination.expenseIdArg])

    init {
        viewModelScope.launch {
            expenseUiState = incExpRepository.getIncExpStream(expenseId)
                .filterNotNull()
                .first()
                .toExpenseUiState()
        }
    }

    suspend fun updateExpense() {
        if (validateInput(expenseUiState.expenseDetails)) {
            incExpRepository.updateExpense(expenseUiState.expenseDetails.toIncExp())
        }
    }

    fun updateUiState(expenseDetails: ExpenseDetails) {
        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))
    }
    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails) : Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
        }
    }
}