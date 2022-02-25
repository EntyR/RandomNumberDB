package com.harman.roomdbapp.enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RandomNumberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val number: Int
)