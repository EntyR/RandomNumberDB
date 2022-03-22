package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.datasouce.GravitySensorDataSource
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GravityFluctuationsRepository(
    private val fluctuationDao: IFluctuationDao,
    private val gravitySensor: GravitySensorDataSource
) : IGravityFluctuationsRepository {

    override suspend fun getGravityFluctuationsRecord(): Flow<Float> {
        return gravitySensor.getCensorFlow().map { gravity ->
            gravity.getFluctuation()
        }
    }

    override suspend fun saveRecordsSessionData(data: List<Float>) {
        fluctuationDao.insertNewItems(
            data.map {
                FluctuationEntity(
                    record = it
                )
            }
        )
    }

    override suspend fun saveOneRecord(record: Float) {
        fluctuationDao.addNewRecord(
            FluctuationEntity(record = record)
        )
    }

    override suspend fun deletePreviousRecords() {
        fluctuationDao.deleteAllItems()
    }
}
