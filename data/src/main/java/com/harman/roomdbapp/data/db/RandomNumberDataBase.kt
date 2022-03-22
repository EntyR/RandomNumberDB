package com.harman.roomdbapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.dao.IRandomNumberDao
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.data.enity.RandomNumberEntity

@Database(entities = [RandomNumberEntity::class, FluctuationEntity::class], version = 2)
abstract class RandomNumberDataBase : RoomDatabase() {
    abstract fun randomNumberDao(): IRandomNumberDao

    abstract fun fluctuationDao(): IFluctuationDao
}
