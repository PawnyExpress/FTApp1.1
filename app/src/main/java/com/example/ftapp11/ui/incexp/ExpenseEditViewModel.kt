package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.IncExpRepository

/**
 * ViewModel to retrieve and update an item from the [IncExpRepository]'s data source.
 */
class ExpenseEditViewModel (
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseEditDestination.expenseIdArg])

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails) : Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
        }
    }
}