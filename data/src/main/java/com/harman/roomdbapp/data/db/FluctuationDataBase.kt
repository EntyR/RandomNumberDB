package com.harman.roomdbapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.enity.FluctuationEntity

@Database(entities = [FluctuationEntity::class], version = 1)
abstract class FluctuationDataBase : RoomDatabase() {
    abstract fun getDao(): IFluctuationDao
}
