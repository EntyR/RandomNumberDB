package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.model.GravityValue
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.flow.Flow

class GravityFluctuationUseCase(private val repository: IGravityFluctuationsRepository) {

    fun addFluctuationRecord(previousValue: GravityValue, currentValue: GravityValue) {
        repository.addGravityRecord(currentValue.getFluctuation(previousValue))
    }

    fun getFluctuationsRecord(): Flow<List<Int>> {
        return repository.getGravityFluctuationsRecord()
    }

    fun updateDb() {
        repository.updateDb()
    }

}