package com.harman.roomdbapp.data

import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.data.enity.RandomNumberEntity
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.model.RandomNumber

fun RandomNumber.convertToEntity(): RandomNumberEntity {
    return RandomNumberEntity(
        number = this.number
    )
}

fun RandomNumberEntity.convertToRNumber(): RandomNumber {
    return RandomNumber(
        this.number
    )
}

fun FluctuationEntity.toGravityRecord(): GravityRecord {
    return GravityRecord(
        this.record,
        this.timestamp
    )
}

fun GravityRecord.toEntity(): FluctuationEntity {
    return FluctuationEntity(
        record = record,
        timestamp = timestamp
    )
}
