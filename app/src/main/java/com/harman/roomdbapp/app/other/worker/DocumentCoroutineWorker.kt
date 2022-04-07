package com.harman.roomdbapp.app.other.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.harman.roomdbapp.domain.model.DocumentEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.PrintWriter

class DocumentCoroutineWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

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
}
