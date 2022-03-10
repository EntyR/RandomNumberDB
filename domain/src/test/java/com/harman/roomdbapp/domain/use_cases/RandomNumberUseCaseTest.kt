package com.harman.roomdbapp.domain.use_cases

import com.google.common.truth.Truth.assertThat
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RandomNumberUseCaseTest {

    lateinit var repository: IRandomNumberRepository

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun `Verify number are adding to repository`() = runBlocking {

        val randomNumber = RandomNumber(1)
        val list: MutableList<RandomNumber> = mutableListOf()

        coEvery { repository.addNumber(randomNumber) } answers {
            list.add(randomNumber)
        }

        val useCase = RandomNumberUseCase(repository)
        useCase.addNumber(randomNumber)

        assertThat(list.contains(randomNumber)).isTrue()
    }

    @Test
    fun `Verify numbers are returned from repository`() = runBlocking {

        val numbers = listOf<RandomNumber>(
            RandomNumber(1),
            RandomNumber(6)
        )

        every { repository.getNumbers() } returns flowOf(numbers)
        val useCase = RandomNumberUseCase(repository)
        assertThat(useCase.getNumbers().take(1).toList()).containsExactly(numbers).inOrder()
    }
}
