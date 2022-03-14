package com.harman.roomdbapp.app.other

import kotlin.random.Random

object MathUtils {

    fun isNumberEven(number: Int) = number % 2 == 0

    fun generateRandomNumber() = Random.nextInt(0, Int.MAX_VALUE)
}
