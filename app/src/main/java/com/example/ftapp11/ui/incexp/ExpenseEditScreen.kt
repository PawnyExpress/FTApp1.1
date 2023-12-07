package com.example.ftapp11.ui.incexp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ftapp11.FinancialTrackerTopAppBar
import com.example.ftapp11.R
import com.example.ftapp11.ui.AppViewModelProvider
import com.example.ftapp11.ui.navigation.NavigationDestination
import com.example.ftapp11.ui.theme.FinancialTrackerTheme

object ExpenseEditDestination : NavigationDestination {
    override val route = "expense_edit"
    override val titleRes = R.string.edit_expense_title
    const val expenseIdArg = "expenseId"
    val routeWithArgs = "$route/{$expenseIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ExpenseEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExpenseEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold (
        topBar = {
            FinancialTrackerTopAppBar(
                title = stringResource(ExpenseEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp =  onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ExpenseEntryBody(
            expenseUiState = viewModel.expenseUiState,
            onExpenseValueChange = { },
            onSaveClick = { },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseEditScreenPreview() {
    FinancialTrackerTheme {
        ExpenseEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do Nothing*/ })
    }
}