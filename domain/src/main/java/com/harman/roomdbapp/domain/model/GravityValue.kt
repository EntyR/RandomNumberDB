package com.harman.roomdbapp.domain.model

import kotlin.math.abs

data class GravityValue(
    val x: Float,
    val y: Float,
    val z: Float
) {
    fun getFluctuation(): Float {
        return abs(x) + abs(y) + abs(z)
    }
}
