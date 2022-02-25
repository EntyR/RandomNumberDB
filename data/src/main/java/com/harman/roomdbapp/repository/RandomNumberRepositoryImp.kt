package com.harman.roomdbapp.repository

import androidx.lifecycle.LiveData
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.RandomNumberRepository

class RandomNumberRepositoryImp: RandomNumberRepository {
    override suspend fun addNumber(number: RandomNumber) {
        TODO("Not yet implemented")
    }

    override suspend fun getNumbers(): LiveData<List<RandomNumber>> {
        TODO("Not yet implemented")
    }
}