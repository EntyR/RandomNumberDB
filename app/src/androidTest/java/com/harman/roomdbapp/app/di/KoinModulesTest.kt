package com.harman.roomdbapp.app.di

import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules


@RunWith(JUnit4::class)
class KoinModulesTest : KoinTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verify_if_koinModules_are_loaded_correctly() {
        koinApplication {
            androidContext(
                ApplicationProvider.getApplicationContext()
            )
            checkModules {
                modules(viewModelModule, useCaseModule, dataModule, dataBaseModule)
            }
        }

    }
}