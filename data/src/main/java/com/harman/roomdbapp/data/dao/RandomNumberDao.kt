package com.harman.roomdbapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.harman.roomdbapp.data.enity.RandomNumberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomNumberDao {

    @Insert
    suspend fun addNumber(number: RandomNumberEntity)

    @Query("SELECT * from RandomNumberEntity")
    suspend fun getAllNumbers(): Flow<List<RandomNumberEntity>>

}