package com.harman.roomdbapp.data.enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FluctuationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val record: Float,
    val timestamp: Long
)
