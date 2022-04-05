package com.harman.roomdbapp

import com.google.common.truth.Truth
import com.harman.roomdbapp.app.other.addSeconds
import com.harman.roomdbapp.app.other.getFormattedTimeFromMillis
import com.harman.roomdbapp.app.other.getMinutesInMillis
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TimeUtilKtTest {


    @Test
    fun `verify what formatted string returned from method`() {
        val init = 1649151576687L
        val expected = "39:36"

        Truth.assertThat(getFormattedTimeFromMillis(init)).isEqualTo(expected)
    }

    @Test
    fun `verify what minutes returned`() {
        val init = 1649151576687L
        val expected = 2376687
        val result = getMinutesInMillis(init)

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `verify minutes are added`() {
        val init = 1649151576687L
        val seconds = 2L
        val expected = init + seconds * 1000

        val returnedVal = addSeconds(seconds, init)

        Truth.assertThat(returnedVal).isEqualTo(expected)
    }
}
