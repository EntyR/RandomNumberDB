package com.harman.roomdbapp.app.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.harman.roomdbapp.app.di.viewModelModule
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NumberListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {


        startKoin {
            androidContext(
                ApplicationProvider.getApplicationContext()
            )
            modules(viewModelModule)

        }
    }

    @Test
    fun verify_live_data_was_returned_from_view_model() {

    }

    @Test
    fun addNumbers() {
    }
}