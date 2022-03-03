package com.harman.roomdbapp.data

import com.harman.roomdbapp.data.enity.RandomNumberEntity
import com.harman.roomdbapp.domain.model.RandomNumber

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