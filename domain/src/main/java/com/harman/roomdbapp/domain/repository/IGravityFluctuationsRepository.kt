package com.harman.roomdbapp.domain.repository

import kotlinx.coroutines.flow.Flow


interface IGravityFluctuationsRepository {

    fun getGravityFluctuationsRecord(): Flow<List<Int>>

    fun addGravityRecord(fluctuation: Int)

    fun updateDb()
}