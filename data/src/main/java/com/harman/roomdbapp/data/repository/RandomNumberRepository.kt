package com.harman.roomdbapp.data.repository

import com.harman.roomdbapp.data.convertToEntity
import com.harman.roomdbapp.data.convertToRNumber
import com.harman.roomdbapp.data.dao.IRandomNumberDao
import com.harman.roomdbapp.data.db.RandomNumberDataBase
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RandomNumberRepository(private val randomNumberDao: IRandomNumberDao): IRandomNumberRepository {
    override suspend fun addNumber(number: RandomNumber) {
        randomNumberDao.addNumber(number.convertToEntity())
    }

    override fun getNumbers(): Flow<List<RandomNumber>> {

        val list = randomNumberDao.getAllNumbers()
        return list.map{
            it.map { number ->
                number.convertToRNumber()
            }
        }

    }
}