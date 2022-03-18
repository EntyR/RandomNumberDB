package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.datasouce.GravitySensorDataSource
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class GravityFluctuationsRepository(
    private val fluctuationDao: IFluctuationDao,
    private val gravitySensor: GravitySensorDataSource
) : IGravityFluctuationsRepository {

    val stateFlow = MutableStateFlow<List<Float>>(emptyList())

    //TODO Not sure this method will work

    override suspend fun getGravityFluctuationsRecord(): StateFlow<List<Float>> {
        gravitySensor.getCensorEventsFlow().collect {
            val newList: MutableList<Float> = mutableListOf()
            it.onEachIndexed { index, gravityValue ->
                if (index == 0) return@onEachIndexed
                newList.add(gravityValue.getFluctuation(it[index - 1]))
            }
            stateFlow.value = newList
        }

        return stateFlow
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
