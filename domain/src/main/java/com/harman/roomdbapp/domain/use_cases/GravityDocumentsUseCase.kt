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

    fun setLastBackupRecordTimestamp(lastRecord: String) {
        repository.saveLastRecord(lastRecord)
    }

    fun getLastBackupRecordTimestamp(): String = repository.getLastRecord()

    fun getAllGravityRecords(fileName: String) = repository.getGravityRecordsFromDocument(fileName)
}
