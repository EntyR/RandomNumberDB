package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow

class GravityFluctuationUseCase(private val repository: IGravityFluctuationsRepository) {

    fun getFluctuationsFlow(): Flow<Float> {
        return repository.getGravityFluctuationsFlow()
    }

    fun getFluctuationRecords(): Flow<List<GravityRecord>> {
        return repository.getGravityFluctuationsRecords()
    }

    suspend fun addNewItem(record: GravityRecord) {
        repository.saveOneRecord(
            record
        )
    }

    suspend fun deletePreviousValue() {
        repository.deletePreviousRecords()
    }

    suspend fun addNewList(list: List<GravityRecord>) {
        repository.saveRecordsSessionData(list)
    }
}
