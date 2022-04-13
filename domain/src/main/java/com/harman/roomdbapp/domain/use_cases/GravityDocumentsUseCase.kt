package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.repository.IDocumentsRepository

class GravityDocumentsUseCase(
    val repository: IDocumentsRepository
) {
    fun getGravityDocumentsName(): List<String> = repository.getCsvList()

    fun addNewGravityDocument(list: List<GravityRecord>) {
        repository.addNewCsvFile("${list.last().timestamp}.csv", list)
    }

    fun setLastBackupRecordTimestamp(lastRecord: Long) {
        repository.saveLastRecord(lastRecord)
    }

    fun getLastBackupRecordTimestamp() = repository.getLastRecord()

    fun getAllGravityRecords(fileName: String) = repository.getGravityRecordsFromDocument(fileName)

    fun deleteGravityDocument(fileName: String) {
        repository.deleteCsvFile(fileName)
    }
}
