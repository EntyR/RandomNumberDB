package com.harman.roomdbapp.app

import android.app.Application
import com.harman.roomdbapp.app.di.dataBaseModule
import com.harman.roomdbapp.app.di.dataModule
import com.harman.roomdbapp.app.di.useCaseModule
import com.harman.roomdbapp.app.di.viewModelModule
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.harman.roomdbapp.app.di.*
import com.harman.roomdbapp.app.other.worker.DocumentCoroutineWorker
import com.harman.roomdbapp.app.services.SensorService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class RandomNumberApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RandomNumberApplication)
            workManagerFactory()
            modules(
                dataBaseModule,
                workerModule,
                dataModule,
                useCaseModule,
                viewModelModule
            )
        }

        val monitorDataRequest =
            PeriodicWorkRequestBuilder<DocumentCoroutineWorker>(5, TimeUnit.MINUTES)
                .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(monitorDataRequest)
    }
}
