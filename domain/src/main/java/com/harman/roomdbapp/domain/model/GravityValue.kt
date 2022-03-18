package com.harman.roomdbapp.domain.model

import kotlin.math.abs

data class GravityValue(
    val x: Float,
    val y: Float,
    val z: Float
) {
    fun getFluctuation(previousValue: GravityValue): Float {
        return abs(previousValue.x - x) +
            abs(previousValue.y - y) +
            abs(previousValue.z - z)
    }
}
