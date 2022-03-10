package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import kotlinx.coroutines.flow.Flow

class RandomNumberUseCase(private val repository: IRandomNumberRepository) {

    suspend fun addNumber(number: RandomNumber) {
        repository.addNumber(number)
    }

    fun getNumbers(): Flow<List<RandomNumber>> {
        return repository.getNumbers()
    }
}
