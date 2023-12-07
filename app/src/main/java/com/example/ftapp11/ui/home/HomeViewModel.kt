package com.example.ftapp11.ui.home

import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.IncExp

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel() : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val incomeList: List<IncExp> = listOf())