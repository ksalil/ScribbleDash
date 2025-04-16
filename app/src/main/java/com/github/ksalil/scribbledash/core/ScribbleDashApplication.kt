package com.github.ksalil.scribbledash.core

import android.app.Application
import com.github.ksalil.scribbledash.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ScribbleDashApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger(level = Level.DEBUG)
            // Reference Android context
            androidContext(this@ScribbleDashApplication)
            // Load app modules
            modules(appModule)
        }
    }
}