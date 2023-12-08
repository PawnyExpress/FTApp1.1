package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ftapp11.data.IncExpRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an item from the [IncExpRepository]'s data source.
 */
class IncomeEditViewModel (
    savedStateHandle: SavedStateHandle,
    private val incExpRepository: IncExpRepository
) : ViewModel() {
    /**
     * Holds current income ui state
     */
    var incomeUiState by mutableStateOf(IncomeUiState())
    private set

    private val incomeId: Int = checkNotNull(savedStateHandle[IncomeEditDestination.incomeIdArg])

    init {
        viewModelScope.launch {
            incomeUiState = incExpRepository.getIncExpStream(incomeId)
                .filterNotNull()
                .first()
                .toIncomeUiState(true)
        }
    }

    suspend fun updateIncome() {
        if (validateInput(incomeUiState.incomeDetails)) {
            incExpRepository.updateIncome(incomeUiState.incomeDetails.toIncExp())
        }
    }

    fun updateUiState(incomeDetails: IncomeDetails) {
        incomeUiState =
            IncomeUiState(incomeDetails = incomeDetails, isEntryValid = validateInput(incomeDetails))
    }

            private fun validateInput(uiState: IncomeDetails = incomeUiState.incomeDetails) : Boolean {
                return with(uiState) {
                    name.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
                }
            }
}
