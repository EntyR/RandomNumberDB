package com.harman.roomdbapp.db

import androidx.room.Database
import com.harman.roomdbapp.dao.RandomNumberDao
import com.harman.roomdbapp.enity.RandomNumberEntity

@Database(entities = [RandomNumberEntity::class], version = 1)
abstract class RandomNumberDataBase {
    abstract fun getDao(): RandomNumberDao
}