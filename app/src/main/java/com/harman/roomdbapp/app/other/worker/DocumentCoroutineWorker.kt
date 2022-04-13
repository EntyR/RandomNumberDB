package com.harman.roomdbapp.app.other.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.use_cases.GravityDocumentsUseCase
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import kotlinx.coroutines.flow.first

class DocumentCoroutineWorker(
    val context: Context,
    params: WorkerParameters,
    private val fluctuationUseCase: GravityFluctuationUseCase,
    private val documentUseCase: GravityDocumentsUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val records = fluctuationUseCase.getFluctuationRecords().first()
        if (records.isNullOrEmpty())
            return Result.failure()

        val lastRecordCsv = documentUseCase.getLastBackupRecordTimestamp()

        records.filter { it.timestamp > lastRecordCsv }.let {
            if (!it.isNullOrEmpty()) {
                addNewRecords(it)
            }
        }
        return Result.success()
    }

    private fun addNewRecords(value: List<GravityRecord>) {
        documentUseCase.setLastBackupRecordTimestamp(
            value.sortedBy { it.timestamp }
                .last().timestamp
        )
        documentUseCase.addNewGravityDocument(value)
    }

    // TODO delete all logs when start displaying value in fragment
    private suspend fun logValue() {
        documentUseCase.getGravityDocumentsName().last().let {
            Log.d("TAG", "Csv document: $it")
            documentUseCase.getAllGravityRecords(it).forEach { record ->
                Log.d("TAG", "Csv document record: $record")
            }
        }
    }
}
