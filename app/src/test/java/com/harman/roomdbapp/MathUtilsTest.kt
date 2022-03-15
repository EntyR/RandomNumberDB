package com.harman.roomdbapp

import com.google.common.truth.Truth
import com.harman.roomdbapp.app.other.MathUtils
import org.junit.jupiter.api.Test

class MathUtilsTest {
    @Test
    fun `Verify what number is generated randomly`() {
        val firstNumber = MathUtils.generateRandomNumber()
        val secondNumber = MathUtils.generateRandomNumber()

        Truth.assertThat(firstNumber).isNotEqualTo(secondNumber)
    }
}
