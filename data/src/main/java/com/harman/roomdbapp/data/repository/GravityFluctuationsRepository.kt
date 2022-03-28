package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.dto.GravityFluctuation
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

    override suspend fun saveRecordsSessionData(data: List<GravityFluctuation>) {
        fluctuationDao.insertNewItems(
            data.map {
                it.toEntity()
            }
        )
    }

    override suspend fun saveOneRecord(record: GravityFluctuation) {
        fluctuationDao.addNewRecord(
            record.toEntity()
        )
    }

    override suspend fun deletePreviousRecords() {
        fluctuationDao.deleteAllItems()
    }
}
