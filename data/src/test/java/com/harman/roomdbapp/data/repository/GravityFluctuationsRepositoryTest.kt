package com.harman.roomdbapp.data.repository

import com.google.common.truth.Truth
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.domain.datasource.IGravitySensorDataSource
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

    @BeforeEach
    fun setUp() {
        dataSource = mockk(relaxed = true)
        fluctuationDao = mockk(relaxed = true)
    }

    @Test
    fun `verify flow converted properly`() = runBlocking {

        val initValue = GravityValue(1f, 2f, 1f)

        coEvery { dataSource.getSensorFlow() } returns flowOf(initValue)

        val repository = GravityFluctuationsRepository(fluctuationDao, dataSource)

        val returnFlow = repository.getGravityFluctuationsRecord()

        Truth.assertThat(returnFlow.last()).isEqualTo(initValue.getFluctuation())
    }
}
