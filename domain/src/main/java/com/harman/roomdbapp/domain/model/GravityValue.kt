package com.harman.roomdbapp.domain.model

import kotlin.math.abs

data class GravityValue(
    val x: Int,
    val y: Int,
    val z: Int
) {
    fun getFluctuation(previousValue: GravityValue): Int {
        return abs(previousValue.x - x) +
                abs(previousValue.y - y) +
                abs(previousValue.z - z)
    }
}
