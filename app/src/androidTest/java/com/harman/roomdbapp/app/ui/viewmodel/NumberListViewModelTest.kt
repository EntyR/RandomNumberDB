package com.harman.roomdbapp.app.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harman.roomdbapp.app.di.viewModelModule
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin

class NumberListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        startKoin {
            modules(viewModelModule)
        }
    }

    @Test
    fun getNumbers() {
    }

    @Test
    fun addNumbers() {
    }
}