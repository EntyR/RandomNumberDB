package com.harman.roomdbapp.domain.repository

import com.harman.roomdbapp.domain.model.GravityRecord
import kotlinx.coroutines.flow.Flow

interface IGravityFluctuationsRepository {

    fun getGravityFluctuationsFlow(): Flow<Float>

    fun getGravityFluctuationsRecords(): Flow<List<GravityRecord>>

    suspend fun saveRecordsSessionData(data: List<GravityRecord>)

    suspend fun saveOneRecord(record: GravityRecord)

    suspend fun deletePreviousRecords()
}
