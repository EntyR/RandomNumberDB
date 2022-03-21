package com.harman.roomdbapp.domain.datasource

import com.harman.roomdbapp.domain.model.GravityValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IGravityCensorDataSource {

    suspend fun getCensorFlow(): Flow<List<GravityValue>>
}
