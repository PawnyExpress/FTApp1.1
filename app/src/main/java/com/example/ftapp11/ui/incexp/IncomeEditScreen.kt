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

object IncomeEditDestination : NavigationDestination {
    override val route = "income_edit"
    override val titleRes = R.string.edit_income_title
    const val incomeIdArg = "incomeId"
    val routeWithArgs = "$route/{$incomeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun IncomeEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncomeEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold (
        topBar = {
            FinancialTrackerTopAppBar(
                title = stringResource(IncomeEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp =  onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        IncomeEntryBody(
        incomeUiState = viewModel.incomeUiState,
        onIncomeValueChange = { },
        onSaveClick = { },
        modifier = Modifier.padding(innerPadding)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun IncomeEditScreenPreview() {
    FinancialTrackerTheme {
        IncomeEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do Nothing*/ })
    }
}