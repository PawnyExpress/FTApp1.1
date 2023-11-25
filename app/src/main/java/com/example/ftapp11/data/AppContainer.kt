package com.example.ftapp11.data

import android.content.Context

interface AppContainer {
    val incExpRepository: IncExpRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val incExpRepository: IncExpRepository by lazy {
        OfflineFinancesRepository(IncExpDatabase.getDatabase(context).incexpDao())
    }

}