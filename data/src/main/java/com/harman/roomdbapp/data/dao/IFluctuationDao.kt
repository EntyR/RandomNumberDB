package com.harman.roomdbapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harman.roomdbapp.data.enity.FluctuationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IFluctuationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewItems(list: List<FluctuationEntity>)

    @Query("SELECT * from FluctuationEntity")
    fun getGravityFluctuationsRecord(): Flow<List<FluctuationEntity>>

    @Query("DELETE from FluctuationEntity")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewRecord(record: FluctuationEntity)
}
