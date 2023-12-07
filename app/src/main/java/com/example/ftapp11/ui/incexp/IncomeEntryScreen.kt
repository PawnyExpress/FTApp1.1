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
import com.example.ftapp11.R
import com.example.ftapp11.ui.AppViewModelProvider
import com.example.ftapp11.ui.navigation.NavigationDestination
import com.example.ftapp11.ui.theme.FinancialTrackerTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object IncomeEntryDestination : NavigationDestination {
    override val route = "income_entry"
    override val titleRes = R.string.income_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: IncomeExpenseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FinancialTrackerTopAppBar(
                title = stringResource(IncomeEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        IncomeEntryBody(
            incomeUiState = viewModel.incomeUiState,
            onIncomeValueChange = viewModel::updateIncomeUiState,
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
fun IncomeEntryBody(
    incomeUiState: IncomeUiState,
    onIncomeValueChange: (IncomeDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        IncomeInputForm(
            incomeDetails = incomeUiState.incomeDetails,
            onValueChange =  onIncomeValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick =onSaveClick,
            enabled = incomeUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeInputForm(
    incomeDetails: IncomeDetails,
    modifier: Modifier = Modifier,
    onValueChange:(IncomeDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
        value = incomeDetails.name,
        onValueChange = {onValueChange(incomeDetails.copy(name = it))},
        label = { Text(stringResource(R.string.income_name_req)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
        )
        OutlinedTextField(
            value = incomeDetails.amount,
            onValueChange = { onValueChange(incomeDetails.copy(amount = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.income_amount_req))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol)},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = incomeDetails.date,
            onValueChange = { onValueChange(incomeDetails.copy(date = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.income_date_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor =   MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = incomeDetails.type,
            onValueChange ={onValueChange(incomeDetails.copy(type = it)) },
            label = { Text(stringResource(R.string.entry_type)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor =   MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
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
private fun IncomeEntryScreenPreview() {
    FinancialTrackerTheme {
        IncomeEntryBody(
            incomeUiState = IncomeUiState(
                IncomeDetails(
                    name = "Income Name", amount = "1500.00", date = "11/27/23"
                )
            ),
            onIncomeValueChange = {},
            onSaveClick = {})
    }
}

