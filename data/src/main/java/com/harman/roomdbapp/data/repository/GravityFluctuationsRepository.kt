package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.datasouce.GravitySensorDataSource
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GravityFluctuationsRepository(
    private val fluctuationDao: IFluctuationDao,
    private val gravitySensor: GravitySensorDataSource
) : IGravityFluctuationsRepository {

    override suspend fun getGravityFluctuationsRecord(): StateFlow<List<Float>> {
        val list = gravitySensor.getCensorEventsFlow().value
        val newList: MutableList<Float> = mutableListOf()
        list.forEachIndexed { index, gravityValue ->
            if (index == 0) return@forEachIndexed
            newList.add(gravityValue.getFluctuation(list[index - 1]))
        }
        return MutableStateFlow(newList)
    }

    override suspend fun saveRecordSessionData() {
        fluctuationDao.addGravityFluctuationsRecord(
            getGravityFluctuationsRecord().value.map {
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
