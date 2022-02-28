package com.harman.roomdbapp.data.enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RandomNumberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val number: Int
)