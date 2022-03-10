package com.harman.roomdbapp

import com.google.common.truth.Truth
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import com.harman.roomdbapp.extension.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class) // https://jeroenmols.com/blog/2019/01/17/livedatajunit5/
class NumberListViewModelTest {

    private lateinit var useCase: RandomNumberUseCase
    private val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher) // https://stackoverflow.com/a/61667162/12366947
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify numbers are returned from use case when subscribed to live data`() {

        val list = listOf(RandomNumber(1))
        coEvery { useCase.getNumbers() } returns flowOf(list)
        val events = mutableListOf<RandomNumber>()
        val viewModel = NumberListViewModel(useCase)
        viewModel.getNumbers().observeForever {
            events.addAll(it)
        }
        Truth.assertThat(events).containsExactlyElementsIn(list).inOrder()
    }
}
