package com.example.ftapp11.ui.incexp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class IncomeEditViewModel (
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    /**
     * Holds current income ui state
     */
    var incomeUiState by mutableStateOf(IncomeUiState())
    private set

    private val incomeId: checkNotNull(savedStateHandle[IncomeEditDestination.incomeIdArg])

            private fun validateInput(uiState: IncomeUiState = incomeUiState.incomeDetails) : Boolean {
                return with(uiState) {
                    name.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
                }
            }
}
