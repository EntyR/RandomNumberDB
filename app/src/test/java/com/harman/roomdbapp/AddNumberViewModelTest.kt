package com.harman.roomdbapp

import com.google.common.truth.Truth
import com.harman.roomdbapp.app.other.AddNumberState
import com.harman.roomdbapp.app.ui.viewmodel.AddNumberViewModel
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import com.harman.roomdbapp.extension.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class AddNumberViewModelTest {

    private lateinit var useCase: RandomNumberUseCase
    private val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify what isTextAdded is switched`() {
        val viewModel = AddNumberViewModel(useCase, dispatcher)
        val initial = viewModel.textState.value
        var endValue: AddNumberState? = null
        viewModel.textState.observeForever {
            endValue = it
        }
        viewModel.switchState()
        Truth.assertThat(endValue).isNotNull()
        Truth.assertThat(endValue).isNotEqualTo(initial)
        Truth.assertThat(endValue).isNotEqualTo(AddNumberState.Done)
    }

    @Test
    fun `Verify what viewModel is adding new number`() {
        val viewModel = AddNumberViewModel(useCase, dispatcher)
        val number = RandomNumber(1)
        val list: MutableList<RandomNumber> = mutableListOf()
        coEvery { useCase.addNumber(number) } answers {
            list.add(number)
        }
        viewModel.addNumber(number)

        Truth.assertThat(list).containsExactlyElementsIn(listOf(number))
    }
}
