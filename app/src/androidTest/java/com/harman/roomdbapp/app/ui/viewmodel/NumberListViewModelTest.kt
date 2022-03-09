package com.harman.roomdbapp.app.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(AndroidJUnit4::class)
class NumberListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: NumberListViewModel

    lateinit var useCase: RandomNumberUseCase

    @Before
    fun setUp() {
        useCase = mockk(relaxed = true)
        viewModel = NumberListViewModel(useCase, Dispatchers.Default)
    }

    @Test
    fun verify_live_data_was_returned_from_view_model() {

        val list = listOf(RandomNumber(1))

        every { useCase.getNumbers() } returns flowOf(list)



        val value = viewModel.getNumbers().getOrAwaitValue()
        Truth.assertThat(value).isEqualTo(list)
    }

    @Test
    fun addNumbers() = runBlocking {

        val mutableList = mutableListOf<RandomNumber>()
        val randomNumber = RandomNumber(1)
        coEvery { useCase.addNumber(randomNumber) } answers {
            mutableList.add(randomNumber)
        }


        viewModel.addNumbers(number = randomNumber)

        Truth.assertThat(mutableList.contains(randomNumber)).isTrue()

    }
}