package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.DatabaseHandler
import com.example.ftapp11.data.IncExp
import java.text.NumberFormat

class IncomeExpenseEntryViewModel () : ViewModel() {

    private var db : DatabaseHandler = DatabaseHandler(null)
    /**
     * Required to pass context for db access
     */
    fun sendContext(databaseHandler: DatabaseHandler){
        db = databaseHandler;
    }

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
            IncomeUiState(incomeDetails = incomeDetails, isEntryValid = validateIncomeInput(incomeDetails))
    }

    fun updateExpenseUiState(expenseDetails: ExpenseDetails) {
        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateExpenseInput(expenseDetails))
    }
    suspend fun saveIncExp() {
        if (validateIncomeInput()) {
            val field = incomeUiState.incomeDetails.toIncExp()
//            incExpRepository.insertIncome(incomeUiState.incomeDetails.toIncExp())
            db.addNewIncome(field.name, field.amount, field.date)
            println("Validated " + field.name + " " + field.amount + " " + field.date)
        }
        if (validateExpenseInput()) {
            val field = expenseUiState.expenseDetails.toIncExp()
//            incExpRepository.insertExpense(expenseUiState.expenseDetails.toIncExp())
            db.addNewExpense(field.name, field.amount, field.date)
            println("Validated " + field.name + " " + field.amount + " " + field.date)
        }
    }


    private fun validateIncomeInput(uiState: IncomeDetails = incomeUiState.incomeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
        }
    }

    private fun validateExpenseInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {
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
    val type: String = "Income",
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
    val type: String = "Expense",
    val name: String = "",
    val amount: String = "",
    val date: String = "",
)

fun IncomeDetails.toIncExp(): IncExp = IncExp(
    id = id,
    type = type,
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0,
    date = date
)

fun ExpenseDetails.toIncExp(): IncExp = IncExp(
    id = id,
    type = type,
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0,
    date = date
)

fun IncExp.formattedAmount(): String {
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
    type = type,
    name = name,
    amount = amount.toString(),
    date = date
)

fun IncExp.toExpenseDetails(): ExpenseDetails = ExpenseDetails(
    id = id,
    type = type,
    name = name,
    amount = amount.toString(),
    date = date
)