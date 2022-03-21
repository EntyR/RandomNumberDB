package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.datasouce.GravitySensorDataSource
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GravityFluctuationsRepository(
    private val fluctuationDao: IFluctuationDao,
    private val gravitySensor: GravitySensorDataSource
) : IGravityFluctuationsRepository {

    override suspend fun getGravityFluctuationsRecord(): Flow<List<Float>> {
        return gravitySensor.getCensorFlow().map {
            it.map { gravity ->
                gravity.getFluctuation()
            }
        }
    }

    override suspend fun saveRecordSessionData() {
        fluctuationDao.addGravityFluctuationsRecord(
            getGravityFluctuationsRecord().first().map {
                FluctuationEntity(
                    record = it
                )
            }
        )
    }

    override suspend fun getFluctuationsPreviousSessionRecord(): List<Float> {
        return fluctuationDao.getGravityFluctuationsRecord().map {
            it.record
        }
    }
}
