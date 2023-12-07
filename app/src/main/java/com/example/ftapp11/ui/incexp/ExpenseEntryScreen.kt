package com.example.ftapp11.ui.incexp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ftapp11.FinancialTrackerTopAppBar
import com.example.ftapp11.ui.navigation.NavigationDestination
import com.example.ftapp11.R
import com.example.ftapp11.ui.AppViewModelProvider
import com.example.ftapp11.ui.theme.FinancialTrackerTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object ExpenseEntryDestination : NavigationDestination {
    override val route = "expense_entry"
    override val titleRes = R.string.expense_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: IncomeExpenseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FinancialTrackerTopAppBar(
                title = stringResource(ExpenseEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        ExpenseEntryBody(
            expenseUiState = viewModel.expenseUiState,
            onExpenseValueChange = viewModel::updateExpenseUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveIncExp()
                    navigateBack
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun ExpenseEntryBody(
    expenseUiState: ExpenseUiState,
    onExpenseValueChange: (ExpenseDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        ExpenseInputForm(
            expenseDetails = expenseUiState.expenseDetails,
            onValueChange =  onExpenseValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick =onSaveClick,
            enabled = expenseUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}

@Composable
fun ExpenseInputForm(
    expenseDetails: ExpenseDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ExpenseDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = expenseDetails.name,
            onValueChange = {onValueChange(expenseDetails.copy(name = it))},
            label = { Text(stringResource(R.string.expense_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = expenseDetails.amount,
            onValueChange = { onValueChange(expenseDetails.copy(amount = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.expense_amount_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = expenseDetails.date,
            onValueChange = { onValueChange(expenseDetails.copy(date = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.expense_date_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                unfocusedContainerColor =   MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = expenseDetails.type,
            onValueChange ={onValueChange(expenseDetails.copy(type = it)) },
            label = { Text(stringResource(R.string.entry_type)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                unfocusedContainerColor =   MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseEntryScreenPreview() {
    FinancialTrackerTheme {
        ExpenseEntryBody(
            expenseUiState = ExpenseUiState(
                ExpenseDetails(
                    name = "Expense Name", amount = "900.00", date = "11/27/23"
                )
            ),
            onExpenseValueChange = {},
            onSaveClick = {})
    }
}