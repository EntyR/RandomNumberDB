package com.harman.roomdbapp.data

import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.data.enity.RandomNumberEntity

fun RandomNumber.convertToEntity(): RandomNumberEntity {
    return RandomNumberEntity(
        0, this.number
    )
}
fun RandomNumberEntity.convertToRNumber(): RandomNumber {
    return RandomNumber(
        this.number
    )
}