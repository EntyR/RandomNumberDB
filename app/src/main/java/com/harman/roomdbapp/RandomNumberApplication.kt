package com.harman.roomdbapp

import android.app.Application
import com.harman.roomdbapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomNumberApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@RandomNumberApplication)
            modules(appModule)
        }
    }
}