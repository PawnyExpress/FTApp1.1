package com.example.ftapp11.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ftapp11.FinancialTrackerApplication
import com.example.ftapp11.ui.home.HomeViewModel
import com.example.ftapp11.ui.incexp.ExpenseDetailsViewModel
import com.example.ftapp11.ui.incexp.ExpenseEditViewModel
import com.example.ftapp11.ui.incexp.IncomeDetailsViewModel
import com.example.ftapp11.ui.incexp.IncomeEditViewModel
import com.example.ftapp11.ui.incexp.IncomeExpenseEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for IncomeEditViewModel
        initializer {
            IncomeEditViewModel(
                this.createSavedStateHandle(),
                financialTrackerApplication().container.incExpRepository
            )
        }
        // Initializer for IncomeEntryViewModel
        initializer {
            IncomeExpenseEntryViewModel(
                financialTrackerApplication().container.incExpRepository)
        }

        // Initializer for IncomeDetailsViewModel
        initializer {
            IncomeDetailsViewModel(
                this.createSavedStateHandle(),
                financialTrackerApplication().container.incExpRepository
            )
        }
        // Initializer for ExpenseEditViewModel
        initializer {
            ExpenseEditViewModel(
                this.createSavedStateHandle(),
                financialTrackerApplication().container.incExpRepository
            )
        }
        // Initializer for ExpenseEntryViewModel
        initializer {
            IncomeExpenseEntryViewModel(
                financialTrackerApplication().container.incExpRepository)
        }

        // Initializer for ExpenseDetailsViewModel
        initializer {
            ExpenseDetailsViewModel(
                this.createSavedStateHandle(),
                financialTrackerApplication().container.incExpRepository

            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(financialTrackerApplication().container.incExpRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [FinancialTrackerApplication].
 */
fun CreationExtras.financialTrackerApplication(): FinancialTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FinancialTrackerApplication)