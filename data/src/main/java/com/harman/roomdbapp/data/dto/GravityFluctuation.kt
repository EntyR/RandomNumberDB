package com.harman.roomdbapp.data.dto

import com.harman.roomdbapp.data.enity.FluctuationEntity

data class GravityFluctuation(
    val record: Float,
    val timestamp: Float
) {
    fun toEntity(): FluctuationEntity {
        return FluctuationEntity(
            record = record,
            timestamp = timestamp
        )
    }
}
fun FluctuationEntity.toGravityFluctuation(): GravityFluctuation {
    return GravityFluctuation(
        this.record,
        this.timestamp
    )
}
