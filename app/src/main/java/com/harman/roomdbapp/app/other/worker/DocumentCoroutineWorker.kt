package com.harman.roomdbapp.app.other.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.harman.roomdbapp.app.other.MemoryUtil
import com.harman.roomdbapp.app.other.MemoryUtil.Companion.convertToCsv
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.enity.FluctuationEntity
import com.harman.roomdbapp.data.toGravityRecord
import kotlinx.coroutines.flow.first

class DocumentCoroutineWorker(
    val context: Context,
    params: WorkerParameters,
    private val db: IFluctuationDao
) : CoroutineWorker(context, params) {

    private val memoryUtil = MemoryUtil(context)

    override suspend fun doWork(): Result {

        val value = db.getGravityFluctuationsRecord().first()
        if (value.isNullOrEmpty())
            return Result.failure()

        val lastItemCsv = value.last().toGravityRecord().convertToCsv()

        val lastDbEntry = value.find {
            it.toGravityRecord().convertToCsv() == memoryUtil.getLastRecord()
        }

        when {
            lastItemCsv == memoryUtil.getLastRecord() -> {
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

    private  fun addNewRecords(value: List<FluctuationEntity>) {
        memoryUtil.saveToSharedPref(value.last().toGravityRecord().convertToCsv())
        memoryUtil.addNewCsvFile(
            "${value.last().timestamp}.csv",
            value.map {
                it.toGravityRecord()
            }
        )
    }

    // TODO delete all logs when start displaying value in fragment
    private suspend fun logValue() {
        memoryUtil.getCsvList().last().let {
            Log.d("TAG", "Csv document: $it")

            memoryUtil.readCsv(it.fileName).forEach { record ->
                Log.d("TAG", "Csv document record: $record")
            }
        }
    }
}
