package com.harman.roomdbapp.domain.datasource

import com.harman.roomdbapp.domain.model.GravityValue
import kotlinx.coroutines.flow.Flow

interface IGravitySensorDataSource {

    fun getSensorFlow(): Flow<GravityValue>
}
