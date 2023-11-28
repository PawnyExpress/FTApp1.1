package com.example.ftapp11.ui.incexp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.IncExpRepository

class IncomeDetailsViewModel (
    savedState: SavedStateHandle
) : ViewModel() {
    private val incomeId : Int = checkNotNull(savedStateHandle[IncomeDetailsDestination.incomeIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IncomeDetailsUiState(
    val outOfStock: Boolean = true,
    val incomeDetails: IncomeDetails = IncomeDetails()
)