package com.harman.roomdbapp.app

import android.app.Application
import com.harman.roomdbapp.app.di.dataBaseModule
import com.harman.roomdbapp.app.di.dataModule
import com.harman.roomdbapp.app.di.useCaseModule
import com.harman.roomdbapp.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RandomNumberApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RandomNumberApplication)
            modules(
                dataBaseModule,
                dataModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
