package com.Vginfotech.reelapp

import android.app.Application
import com.Vginfotech.reelapp.DI.appModule
import com.Vginfotech.reelapp.DI.networkModule
import com.Vginfotech.reelapp.DI.repositoryModule
import com.Vginfotech.reelapp.DI.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReelApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@ReelApp)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                    appModule
                )
            )
        }
    }
}