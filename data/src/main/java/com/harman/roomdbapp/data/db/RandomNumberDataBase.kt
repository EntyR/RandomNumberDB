package com.harman.roomdbapp.data.db

import androidx.room.Database
import com.harman.roomdbapp.data.dao.RandomNumberDao
import com.harman.roomdbapp.data.enity.RandomNumberEntity

@Database(entities = [RandomNumberEntity::class], version = 1)
abstract class RandomNumberDataBase {
    abstract fun getDao(): RandomNumberDao
}