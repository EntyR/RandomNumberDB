package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.repository.IDocumentsRepository

class GravityDocumentsUseCase(
    val repository: IDocumentsRepository
) {
    fun getGravityDocumentsName(): List<String> = repository.getCsvList()

    fun addNewGravityDocument(fileName: String, list: List<GravityRecord>) {
        repository.addNewCsvFile(fileName, list)
    }

    fun storeLastRecord(lastRecord: String) {
        repository.saveLastRecord(lastRecord)
    }

    fun getLastRecord(): String = repository.getLastRecord()

    fun getAllGravityRecords(fileName: String) = repository.getGravityRecordsFromDocument(fileName)
}
