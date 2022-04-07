package com.harman.roomdbapp.app.other.worker

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.toGravityRecord
import com.harman.roomdbapp.domain.model.DocumentEntry
import com.harman.roomdbapp.domain.model.GravityRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.PrintWriter

class DocumentCoroutineWorker(val context: Context, params: WorkerParameters, val db: IFluctuationDao) :
    CoroutineWorker(context, params) {

    private var currentList: MutableList<GravityRecord> = mutableListOf()

    override suspend fun doWork(): Result {

        val value = db.getGravityFluctuationsRecord().asLiveData().value?.map {
            it.toGravityRecord()
        }

        if (value.isNullOrEmpty())
            return Result.failure()

        if (value.containsAll(currentList))
            return Result.success()
        else {
            value.filter {
                currentList.contains(it)
            }.forEach { newItem ->
                addNewEntry(newItem.convertToCsv())
            }

        }


        return Result.success()
    }

    private suspend fun getCsvList(): List<DocumentEntry> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".csv") }?.map {
                val lines = it.readLines()
                val lastLine = if (lines.isNotEmpty()) lines.last() else ""
                val lastLineList = if (lastLine.isNotBlank()) lastLine.split(",") else emptyList()
                val timeStamp = if (lastLineList.size > 1) lastLineList.last() else ""
                DocumentEntry(it.name, timeStamp)
            } ?: listOf()
        }
    }

    private fun addNewCsvFile(filename: String): Boolean {
        return try {
            val file = File(context.filesDir, filename)
            file.createNewFile()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun GravityRecord.convertToCsv(): String {
        return "${this.record},${this.timestamp}"
    }

    private fun addNewEntry(filename: String, csvString: String): Boolean {
        return try {
            val file = File(context.filesDir, filename)
            val pw = PrintWriter(file)
            pw.println(csvString)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun readCsv(filename: String): List<GravityRecord> {
        val file = File(context.filesDir, filename)
        val resultList = file.readLines().let {
            it.map { line ->
                val array = line.split(",")
                GravityRecord(array[0].toFloat(), array[1].toLong())
            }
        }
        return resultList

    }



}
