package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.toEntity
import com.harman.roomdbapp.data.toGravityRecord
import com.harman.roomdbapp.domain.datasource.IGravitySensorDataSource
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GravityFluctuationsRepository(
    private val fluctuationDao: IFluctuationDao,
    private val gravitySensor: IGravitySensorDataSource
) : IGravityFluctuationsRepository {

    override fun getGravityFluctuationsFlow(): Flow<Float> {
        return gravitySensor.getSensorFlow().map { gravity ->
            gravity.getFluctuation()
        }.filterNotNull()
    }

    override fun getGravityFluctuationsRecords(): Flow<List<GravityRecord>> {
        return fluctuationDao.getGravityFluctuationsRecord().map {
            it.map { record ->
                record.toGravityRecord()
            }
        }
    }

    override suspend fun saveRecordsSessionData(data: List<GravityRecord>) {
        fluctuationDao.insertNewItems(
            data.map {
                it.toEntity()
            }
        )
    }

    override suspend fun saveOneRecord(record: GravityRecord) {
        fluctuationDao.addNewRecord(
            record.toEntity()
        )
    }

    override suspend fun deletePreviousRecords() {
        fluctuationDao.deleteAllItems()
    }
}
