package com.example.ftapp11.ui.incexp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ftapp11.FinancialTrackerTopAppBar
import com.example.ftapp11.R
import com.example.ftapp11.data.DatabaseHandler
import com.example.ftapp11.data.IncExp
import com.example.ftapp11.ui.AppViewModelProvider
import com.example.ftapp11.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object IncomeDetailsDestination : NavigationDestination {
    override val route = "income_details"
    override val titleRes = R.string.income_detail_title
    const val incomeIdArg = "incomeId"
    val routeWithArgs = "$route/{$incomeIdArg}"
}
var dbInt : Int = 100
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeDetailsScreen(
    navigateToEditIncome: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    databaseHandler : DatabaseHandler,
    viewModel: IncomeDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

//    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val data : IncExp = databaseHandler.getIncome(viewModel.getIncomeId())
    Scaffold(
        topBar = {
            FinancialTrackerTopAppBar(
                title = stringResource(IncomeDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
//                onClick = { navigateToEditIncome(uiState.value.incomeDetails.id) },
                onClick = { navigateToEditIncome(dbInt) }, /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_income_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        IncomeDetailsBody(
            data,
            incomeDetailsUiState = IncomeDetailsUiState(),
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteIncome()
                    navigateBack()
                }
                       },
            modifier = Modifier                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            databaseHandler,
            viewModel
        )
    }
}

@Composable
private fun IncomeDetailsBody(
    data : IncExp,
    incomeDetailsUiState: IncomeDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    databaseHandler : DatabaseHandler,
    viewModel: IncomeDetailsViewModel
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        IncomeDetails(
            data,
            incExp = incomeDetailsUiState.incomeDetails.toIncExp(),
            modifier = Modifier.fillMaxWidth(),
            databaseHandler,
            viewModel
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                    databaseHandler.deleteIncomeEntry(data);
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun IncomeDetails(
    data : IncExp, incExp: IncExp, modifier: Modifier = Modifier, databaseHandler : DatabaseHandler,
    viewModel: IncomeDetailsViewModel
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            IncomeDetailsRow(
                labelResID = R.string.income,
                incomeDetail = data.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            IncomeDetailsRow(
                labelResID = R.string.date,
                incomeDetail = data.date,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            IncomeDetailsRow(
                labelResID = R.string.amount,
                incomeDetail = data.formattedAmount(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
        }

    }
}

@Composable
private fun IncomeDetailsRow(
    @StringRes labelResID: Int, incomeDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = incomeDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}