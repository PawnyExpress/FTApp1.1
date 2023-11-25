package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.IncExpRepository
import java.util.Date

class IncomeExpenseEntryViewModel (private val incexpRepository: IncExpRepository) : ViewModel() {
    /**
     * Holds current income ui state
     */
    var incomeUiState by mutableStateOf(IncomeUiState())
        private set
    /**
     * Holds current expense ui state
     */
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    fun updateIncomeUiState(incomeDetails : IncomeDetails) {
        incomeUiState =
            IncomeUIState(incomeDetails = incomeDetails, isEntryValid = validateInput(incomeDetails))
    }

    fun updateExpenseUiState(expenseDetails : ExpenseDetails) {
        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))
    }
    suspend fun saveIncExp() {
        if (validateInput()) {
            IncExpRepository.insertIncExp(incomeUiState.incomeDetails.toItem())
        }
    }

    suspend fun saveIncExp() {
        if (validateInput()) {
            IncExpRepository.insertIncExp(expenseUiState.incomeDetails.toItem())
        }
    }

    private fun validateInput(uiState: IncomeDetails = incomeUiState.incomeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
        }
    }
}

/**
 * Represents Ui State for the Income.
 */
data class IncomeUiState(
    val incomeDetails: IncomeDetails = IncomeDetails(),
    val isEntryValid: Boolean = false
)

data class IncomeDetails(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
    val date: String = "",
)
/**
 * Represents Ui State for the Expenses.
 */
data class ExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false
)
data class ExpenseDetails(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
    val date: String = "",
)