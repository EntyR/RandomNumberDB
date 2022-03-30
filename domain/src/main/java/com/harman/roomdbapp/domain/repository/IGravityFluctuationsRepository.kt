package com.harman.roomdbapp.domain.repository

import com.harman.roomdbapp.domain.model.GravityRecord
import kotlinx.coroutines.flow.Flow

interface IGravityFluctuationsRepository {

    fun getGravityFluctuationsRecord(): Flow<Float>

    suspend fun saveRecordsSessionData(data: List<GravityRecord>)

    suspend fun saveOneRecord(record: GravityRecord)

    suspend fun deletePreviousRecords()
}
