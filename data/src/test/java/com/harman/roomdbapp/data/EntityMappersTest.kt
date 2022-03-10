package com.harman.roomdbapp.data

import com.google.common.truth.Truth
import com.harman.roomdbapp.data.enity.RandomNumberEntity
import com.harman.roomdbapp.domain.model.RandomNumber
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class EntityMappersTest {

    @Test
    fun `Verify conversion to Entity was right`() {

        val expected = RandomNumberEntity(0, 1)
        val initial = RandomNumber(1).convertToEntity()

        Truth.assertThat(initial.id).isEqualTo(expected.id)
        Truth.assertThat(initial.number).isEqualTo(expected.number)

    }

    @Test
    fun `Verify conversion to Number was right`() {
        val initial = RandomNumberEntity(0, 1).convertToRNumber()
        val expected = RandomNumber(1)

        Truth.assertThat(initial.number).isEqualTo(expected.number)
    }
}