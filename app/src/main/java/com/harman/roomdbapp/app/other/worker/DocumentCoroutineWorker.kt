package com.harman.roomdbapp.app.other.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.harman.roomdbapp.app.other.MemoryUtil
import com.harman.roomdbapp.app.other.MemoryUtil.Companion.convertToCsv
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.toGravityRecord
import kotlinx.coroutines.flow.first
import java.util.UUID

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

        val lastItem = value.last().toGravityRecord().convertToCsv()

        if (lastItem == memoryUtil.getLastRecord()) {
            // TODO delete when start displaying value in fragment
            logValue()
            return Result.success()
        } else {
            memoryUtil.saveToSharedPref(lastItem)
            memoryUtil.addNewCsvFile(
                "${UUID.randomUUID()}.csv",
                value.map {
                    it.toGravityRecord()
                }
            )
        }
        // TODO delete when start displaying value in fragment
        logValue()

        return Result.success()
    }

    private suspend fun logValue() {
        memoryUtil.getCsvList().last().let {
            Log.d("TAG", "Csv document: $it")

            memoryUtil.readCsv(it.fileName).forEach { record ->
                Log.d("TAG", "Csv document record: $record")
            }
        }
    }
}
