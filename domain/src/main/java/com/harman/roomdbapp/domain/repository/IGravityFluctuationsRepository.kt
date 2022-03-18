package com.harman.roomdbapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface IGravityFluctuationsRepository {

    suspend fun getGravityFluctuationsRecord(): Flow<List<Float>>

    suspend fun saveRecordSessionData()

    suspend fun getFluctuationsPreviousSessionRecord(): List<Float>
}
