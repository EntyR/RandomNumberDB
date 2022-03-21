package com.harman.roomdbapp.data.dao

import androidx.room.*
import com.harman.roomdbapp.data.enity.FluctuationEntity

@Dao
abstract class IFluctuationDao {

    @Transaction
    suspend fun replaceGravityFluctuationsRecord(records: List<FluctuationEntity>){
        deleteAllItems()
        insertNewItems(records)
    }

    @Insert
    abstract fun insertNewItems(list: List<FluctuationEntity>)

    @Query("SELECT * from FluctuationEntity")
    abstract suspend fun getGravityFluctuationsRecord(): List<FluctuationEntity>

    @Query("DELETE from FluctuationEntity")
    abstract suspend fun  deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addNewRecord(record: FluctuationEntity)

}
