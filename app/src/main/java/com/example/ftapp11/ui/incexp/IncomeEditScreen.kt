package com.example.ftapp11.ui.incexp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ftapp11.FinancialTrackerTopAppBar
import com.example.ftapp11.R
import com.example.ftapp11.data.DatabaseHandler
import com.example.ftapp11.data.IncExp
import com.example.ftapp11.ui.AppViewModelProvider
import com.example.ftapp11.ui.navigation.NavigationDestination

object IncomeEditDestination : NavigationDestination {
    override val route = "income_edit"
    override val titleRes = R.string.edit_income_title
    const val incomeIdArg = "incomeId"
    val routeWithArgs = "$route/{$incomeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun IncomeEditScreen(
    databaseHandler : DatabaseHandler,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncomeEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val data : IncExp = databaseHandler.getIncome(viewModel.getIncomeId())

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
            databaseHandler,
            true,
            data,
        incomeUiState = viewModel.incomeUiState,
        onIncomeValueChange = {viewModel::updateUiState },
        onSaveClick = { },
        modifier = Modifier.padding(innerPadding)
        )

    }
}

//@Preview(showBackground = true)
//@Composable
//fun IncomeEditScreenPreview() {
//    FinancialTrackerTheme {
//        IncomeEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do Nothing*/ })
//    }
//}