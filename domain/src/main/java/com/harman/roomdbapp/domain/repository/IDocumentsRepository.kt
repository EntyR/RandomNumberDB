package com.harman.roomdbapp.domain.repository

import com.harman.roomdbapp.domain.model.GravityRecord

interface IDocumentsRepository {

    fun getCsvList(): List<String>

    fun addNewCsvFile(filename: String, list: List<GravityRecord>): Boolean

    fun getGravityRecordsFromDocument(filename: String): List<GravityRecord>

    fun saveLastRecord(newRecord: Long)

    fun getLastRecord(): Long
}
