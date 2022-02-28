package com.harman.roomdbapp.domain.repository

import androidx.lifecycle.LiveData
import com.harman.roomdbapp.domain.model.RandomNumber
import kotlinx.coroutines.flow.Flow

interface RandomNumberRepository {

    suspend fun addNumber(number: RandomNumber)

    suspend fun getNumbers(): Flow<List<RandomNumber>>

}