package com.example.ftapp11.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ftapp11.FinancialTrackerTopAppBar
import com.example.ftapp11.R
import com.example.ftapp11.data.DatabaseHandler
import com.example.ftapp11.data.IncExp
import com.example.ftapp11.ui.incexp.formattedAmount
import com.example.ftapp11.ui.navigation.NavigationDestination
import com.example.ftapp11.ui.theme.FinancialTrackerTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToIncomeEntry: () -> Unit,
    navigateToIncomeUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    databaseHandler: DatabaseHandler
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FinancialTrackerTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToIncomeEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.income_entry_title)
                )
            }
        },

    ) { innerPadding ->
        HomeBody(
            incExpList = listOf(),

            onItemClick = navigateToIncomeUpdate,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            databaseHandler
        )
    }


}
@Composable
private fun HomeBody(
    incExpList: List<IncExp>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier, databaseHandler : DatabaseHandler
) {
    var incExpList = databaseHandler.getIncome()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        if (incExpList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            FinancesList(
                incExpList = incExpList,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun FinancesList(
    incExpList: List<IncExp>, onItemClick: (IncExp) -> Unit, modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        items(items = incExpList, key = { it.id }) { incExp ->
            InventoryItem(incExp = incExp,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(incExp) })
        }
    }
}

@Composable
private fun InventoryItem(
    incExp: IncExp, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = incExp.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = incExp.formattedAmount(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeBodyPreview() {
//    FinancialTrackerTheme {
//        HomeBody(listOf(
//            IncExp(1, "Work", 1000.0, "10/01/23"), IncExp(2, "Freelancing", 200.0, "10/01/23"), IncExp(3, "Work Supplies", 300.0, "10/01/23")
//        ), onItemClick = {})
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeBodyEmptyListPreview() {
//    FinancialTrackerTheme {
//        HomeBody(listOf(), onItemClick = {})
//    }
//}

@Preview(showBackground = true)
@Composable
fun IncomeItemPreview() {
    FinancialTrackerTheme {
        InventoryItem(
            IncExp(1, "Work", 1000.0, "10/01/23"),
        )
    }
}
