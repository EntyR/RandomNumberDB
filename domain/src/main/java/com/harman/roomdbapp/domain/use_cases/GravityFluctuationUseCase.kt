package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow

class GravityFluctuationUseCase(private val repository: IGravityFluctuationsRepository) {

    suspend fun getPreviousRecord(): List<Float> {
        return repository.getFluctuationsPreviousSessionRecord()
    }

    suspend fun getFluctuationsRecord(): Flow<List<Float>> {
        return repository.getGravityFluctuationsRecord()
    }

    suspend fun updateDb() {
        repository.saveRecordSessionData()
    }
}
