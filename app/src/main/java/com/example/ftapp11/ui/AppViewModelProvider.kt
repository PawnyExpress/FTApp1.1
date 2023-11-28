package com.example.ftapp11.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ftapp11.FinancialTrackerApplication
import com.example.ftapp11.ui.home.HomeViewModel
import com.example.ftapp11.ui.incexp.IncomeEditViewModel
import com.example.ftapp11.ui.incexp.IncomeExpenseEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            IncomeEditViewModel(
                this.createSavedStateHandle()
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            IncomeExpenseEntryViewModel(FinancialTrackerApplication().container.itemsRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            IncomeDetailsViewModel(
                this.createSavedStateHandle()
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [FinancialTrackerApplication].
 */
fun CreationExtras.financialtrackerApplication(): FinancialTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinancialTrackerApplication)