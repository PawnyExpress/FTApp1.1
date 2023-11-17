package com.example.ftapp11;

import android.app.Application
        import com.example.ftapp11.AppContainer
                import com.example.ftapp11.
public class FinancialTrackerApplication : Application(){
    lateinit var container: AppContainer

        override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        }
}
