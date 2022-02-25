package com.harman.roomdbapp.domain.use_cases

import androidx.lifecycle.LiveData
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.RandomNumberRepository

class GetAllNumbersUseCase(private val repository: RandomNumberRepository) {

    suspend operator fun invoke(): LiveData<List<RandomNumber>> {
        return repository.getNumbers()
    }

}