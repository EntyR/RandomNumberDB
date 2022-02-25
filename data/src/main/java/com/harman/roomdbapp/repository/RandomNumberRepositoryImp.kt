package com.harman.roomdbapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.harman.roomdbapp.convertToEntity
import com.harman.roomdbapp.convertToRNumber
import com.harman.roomdbapp.db.RandomNumberDataBase
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.RandomNumberRepository

class RandomNumberRepositoryImp(private val db: RandomNumberDataBase): RandomNumberRepository {
    override suspend fun addNumber(number: RandomNumber) {
        db.getDao().addNumber(number.convertToEntity())
    }

    override suspend fun getNumbers(): LiveData<List<RandomNumber>> {

        val liveData = db.getDao().getAllNumbers()
        return Transformations.map(liveData){
            it.map { number ->
                number.convertToRNumber()
            }
        }

    }
}