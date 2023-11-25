package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.IncExp
import com.example.ftapp11.data.IncExpRepository
import java.text.NumberFormat
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
            IncExpRepository.insertIncExp(incomeUiState.incomeDetails.toIncome())
        }
    }

    suspend fun saveIncExp() {
        if (validateInput()) {
            IncExpRepository.insertIncExp(expenseUiState.incomeDetails.toExpense())
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

fun IncomeDetails.toIncome(): IncExp = IncExp(
    id = id,
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0,
    date = date
)

fun ExpenseDetails.toExpense(): IncExp = IncExp(
    id = id,
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0,
    date = date
)

fun IncExp.formatedAmount(): String {
    return NumberFormat.getCurrencyInstance().format(amount)
}

fun IncExp.toIncomeUiState(isEntryValid: Boolean = false): IncomeUiState = IncomeUiState(
    incomeDetails = this.toIncomeDetails(),
    isEntryValid = isEntryValid
)

fun IncExp.toExpenseUiState(isEntryValid: Boolean = false): ExpenseUiState = ExpenseUiState(
    expenseDetails = this.toExpenseDetails(),
    isEntryValid = isEntryValid
)

fun IncExp.toIncomeDetails(): IncomeDetails = IncomeDetails(
    id = id,
    name = name,
    amount = amount.toString(),
    date = date.toString()
)

fun IncExp.toExpenseDetails(): ExpenseDetails = ExpenseDetails(
    id = id,
    name = name,
    amount = amount.toString(),
    date = date.toString()
)