package com.harman.roomdbapp.domain.repository

import com.harman.roomdbapp.domain.model.RandomNumber
import kotlinx.coroutines.flow.Flow

interface IRandomNumberRepository {

    suspend fun addNumber(number: RandomNumber)

    fun getNumbers(): Flow<List<RandomNumber>>

}