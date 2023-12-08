package com.example.ftapp11.ui.incexp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


class IncomeDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val incomeObject : Int = checkNotNull(savedStateHandle[IncomeDetailsDestination.incomeIdArg])

    fun getIncomeId(): Int {

        return incomeObject
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IncomeDetailsUiState(
    val incomeDetails: IncomeDetails = IncomeDetails()
)