package com.harman.roomdbapp.app.other.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.harman.roomdbapp.data.repository.DocumentRepository.Companion.convertToCsv
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

        val value = fluctuationUseCase.getFluctuationRecords().first()
        if (value.isNullOrEmpty())
            return Result.failure()

        val lastItemCsv = value.last().convertToCsv()

        val lastDbEntry = value.find {
            it.convertToCsv() == documentUseCase.getLastRecord()
        }

        when {
            lastItemCsv == documentUseCase.getLastRecord() -> {
                logValue()
                return Result.success()
            }
            lastDbEntry != null -> {
                val list = value.dropWhile {
                    it != lastDbEntry
                }
                addNewRecords(list)
                logValue()
                return Result.success()
            }
            else -> {
                addNewRecords(value)
                logValue()
                return Result.success()
            }
        }
    }

    private fun addNewRecords(value: List<GravityRecord>) {
        documentUseCase.storeLastRecord(value.last().convertToCsv())
        documentUseCase.addNewGravityDocument(
            "${value.last().timestamp}.csv", value
        )
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
