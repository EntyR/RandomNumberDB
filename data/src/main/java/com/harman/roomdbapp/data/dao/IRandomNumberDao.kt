package com.harman.roomdbapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.harman.roomdbapp.data.enity.RandomNumberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IRandomNumberDao {

    @Insert
    suspend fun addNumber(number: RandomNumberEntity)

    @Query("SELECT * from RandomNumberEntity")
    fun getAllNumbers(): Flow<List<RandomNumberEntity>>
}
