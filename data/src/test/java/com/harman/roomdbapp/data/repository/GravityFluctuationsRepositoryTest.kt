package com.harman.roomdbapp.data.repository

import com.google.common.truth.Truth
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.domain.datasource.IGravitySensorDataSource
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.model.GravityValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GravityFluctuationsRepositoryTest {

    lateinit var dataSource: IGravitySensorDataSource
    lateinit var fluctuationDao: IFluctuationDao

    lateinit var database: IFluctuationDao

    @BeforeEach
    fun setUp() {
        database = mockk(relaxed = true)

        dataSource = mockk(relaxed = true)
        fluctuationDao = mockk(relaxed = true)
    }

    @Test
    fun `verify flow converted properly`() = runBlocking {

        val initValue = GravityValue(1f, 2f, 1f)

        coEvery { dataSource.getSensorFlow() } returns flowOf(initValue)

        val repository = GravityFluctuationsRepository(fluctuationDao, dataSource)

        val returnFlow = repository.getGravityFluctuationsFlow()

        Truth.assertThat(returnFlow.last()).isEqualTo(initValue.getFluctuation())
    }

    @Test
    fun `verify items are added into database`(): Unit = runBlocking {
        val value = 2f
        val init = GravityRecord(value, 123)

        val expected = listOf(FluctuationEntity(record = value, timestamp = 123))
        var fakeList = mutableListOf<FluctuationEntity>()

        coEvery { database.insertNewItems(expected) } answers {
            fakeList = expected.toMutableList()
        }
        val repository = GravityFluctuationsRepository(database, dataSource)
        repository.saveRecordsSessionData(listOf(init))
        Truth.assertThat(fakeList).isEqualTo(expected)
    }

    @Test
    fun `verify item is added into database`(): Unit = runBlocking {
        val value = 2f

        val expected = FluctuationEntity(record = value, timestamp = 123)
        val fakeList = mutableListOf<FluctuationEntity>(expected)

        coEvery { database.deleteAllItems() } answers {
            fakeList.removeAll { true }
        }
        val repository = GravityFluctuationsRepository(database, dataSource)
        repository.deletePreviousRecords()
        Truth.assertThat(fakeList).isEmpty()
    }
}
