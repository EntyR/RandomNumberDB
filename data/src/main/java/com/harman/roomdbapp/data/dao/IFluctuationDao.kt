package com.harman.roomdbapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.harman.roomdbapp.data.enity.FluctuationEntity

@Dao
interface IFluctuationDao {

    @Insert
    suspend fun addGravityFluctuationsRecord(records: List<FluctuationEntity>)

    @Query("SELECT * from FluctuationEntity")
    suspend fun getGravityFluctuationsRecord(): List<FluctuationEntity>
}
