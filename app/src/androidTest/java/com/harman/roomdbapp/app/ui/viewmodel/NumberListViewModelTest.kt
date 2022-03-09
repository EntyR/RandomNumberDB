package com.harman.roomdbapp.app.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.harman.roomdbapp.data.repository.RandomNumberRepository
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.Parameterized
import kotlin.test.BeforeTest


@RunWith(AndroidJUnit4::class)
class NumberListViewModelTest {




    private lateinit var useCase: RandomNumberUseCase
    lateinit var viewModel: NumberListViewModel
    lateinit var repo: IRandomNumberRepository


    @Before
    fun setUp() {
        repo = mockk(relaxed = true)
        useCase = RandomNumberUseCase(repo)
    }

    @Test
    fun verify_live_data_was_returned_from_view_model() = runBlocking {

        val list = listOf(RandomNumber(1))
        every {
            repo.getNumbers()
        } returns flowOf(list)



        viewModel = NumberListViewModel(useCase, Dispatchers.Main)

        withContext(Dispatchers.Main){
            val value = viewModel.getNumbers().getOrAwaitValue()
            Truth.assertThat(value).isEqualTo(list)
        }

    }

    @Test
    fun addNumbers() = runBlocking {

        val mutableList = mutableListOf<RandomNumber>()
        val randomNumber = RandomNumber(1)
        coEvery { useCase.addNumber(randomNumber) } answers {
            mutableList.add(randomNumber)
        }
        viewModel = NumberListViewModel(useCase, Dispatchers.Default)


        viewModel.addNumbers(number = randomNumber)

        Truth.assertThat(mutableList.contains(randomNumber)).isTrue()

    }
}