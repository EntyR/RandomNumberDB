package com.harman.roomdbapp

import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.enity.RandomNumberEntity

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