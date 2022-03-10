package com.harman.roomdbapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harman.roomdbapp.data.dao.IRandomNumberDao
import com.harman.roomdbapp.data.enity.RandomNumberEntity

@Database(entities = [RandomNumberEntity::class], version = 1)
abstract class RandomNumberDataBase : RoomDatabase() {
    abstract fun getDao(): IRandomNumberDao
}
