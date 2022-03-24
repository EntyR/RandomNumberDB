package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.domain.datasource.IGravitySensorDataSource
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GravityFluctuationsRepository(
    private val fluctuationDao: IFluctuationDao,
    private val gravitySensor: IGravitySensorDataSource
) : IGravityFluctuationsRepository {

    override fun getGravityFluctuationsRecord(): Flow<Float> {
        return gravitySensor.getSensorFlow().map { gravity ->
            gravity.getFluctuation()
        }.filterNotNull()
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
