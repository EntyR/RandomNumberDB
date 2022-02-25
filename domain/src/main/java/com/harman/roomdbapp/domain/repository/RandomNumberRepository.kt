package com.harman.roomdbapp.domain.repository

import androidx.lifecycle.LiveData
import com.harman.roomdbapp.domain.model.RandomNumber

interface RandomNumberRepository {

    suspend fun addNumber(number: RandomNumber)

    suspend fun getNumbers(): LiveData<List<RandomNumber>>

}