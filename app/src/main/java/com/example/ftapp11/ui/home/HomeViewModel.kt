package com.example.ftapp11.ui.home

import androidx.lifecycle.ViewModel
import com.example.ftapp11.data.IncExp

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel() : ViewModel() {

//    val homeUiState: StateFlow<HomeUiState> =
//        incExpRepository.getAllIncExpStream().map { HomeUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = HomeUiState()
//
//            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val incExpList: List<IncExp> = listOf())