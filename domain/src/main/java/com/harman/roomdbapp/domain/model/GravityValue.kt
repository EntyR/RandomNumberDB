package com.harman.roomdbapp.domain.model

import kotlin.math.pow
import kotlin.math.sqrt

data class GravityValue(
    val x: Float,
    val y: Float,
    val z: Float
) {
    fun getFluctuation(): Float {
        return sqrt(x.pow(2) + y.pow(2) + x.pow(2))
    }
}
