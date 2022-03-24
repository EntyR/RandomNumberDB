package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow

class GravityFluctuationUseCase(private val repository: IGravityFluctuationsRepository) {

    fun getFluctuationsRecord(): Flow<Float> {
        return repository.getGravityFluctuationsRecord()
    }

    suspend fun addNewItem(float: Float) {
        repository.saveOneRecord(float)
    }

    suspend fun deletePreviousValue() {
        repository.deletePreviousRecords()
    }

    suspend fun addNewList(list: List<Float>) {
        repository.saveRecordsSessionData(list)
    }
}
