package com.harman.roomdbapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.harman.roomdbapp.enity.RandomNumberEntity

@Dao
interface RandomNumberDao {

    @Insert
    suspend fun addNumber(number: RandomNumberEntity)

    @Query("SELECT * from RandomNumberEntity")
    suspend fun getAllNumbers(): LiveData<List<RandomNumberEntity>>

}