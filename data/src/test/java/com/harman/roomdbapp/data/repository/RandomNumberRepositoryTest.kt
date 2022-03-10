package com.harman.roomdbapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.harman.roomdbapp.data.dao.IRandomNumberDao
import com.harman.roomdbapp.data.enity.RandomNumberEntity
import com.harman.roomdbapp.domain.model.RandomNumber
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RandomNumberRepositoryTest {

    lateinit var database: IRandomNumberDao

    @BeforeEach
    fun setUp() {
        database = mockk(relaxed = true)
    }

    @Test
    fun `Verify random numbers are added`() = runBlocking {
        val number = RandomNumber(2)
        val expected = RandomNumberEntity(0, 2)
        val fakeList = mutableListOf<RandomNumberEntity>()

        coEvery { database.addNumber(expected) } answers {
            fakeList.add(expected)
        }
        val repository = RandomNumberRepository(database)
        repository.addNumber(number)
        assertThat(fakeList.contains(expected)).isTrue()
    }

    @Test
    fun `Verify random numbers converted properly`() = runBlocking {
        every { database.getAllNumbers() } returns flowOf(
            listOf(
                RandomNumberEntity(1, 2),
                RandomNumberEntity(2, 4)
            )
        )

        val expectedList = listOf<RandomNumber>(
            RandomNumber(2),
            RandomNumber(4)
        )

        val repository = RandomNumberRepository(database)

        val number = repository.getNumbers().first()

        assertThat(number).isEqualTo(expectedList)
    }
}
