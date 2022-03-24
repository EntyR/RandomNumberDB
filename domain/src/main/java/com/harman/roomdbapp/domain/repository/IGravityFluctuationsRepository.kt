package com.harman.roomdbapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface IGravityFluctuationsRepository {

    fun getGravityFluctuationsRecord(): Flow<Float>

    suspend fun saveRecordsSessionData(data: List<Float>)

    suspend fun saveOneRecord(record: Float)

    suspend fun deletePreviousRecords()
}
