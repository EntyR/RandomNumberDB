package com.harman.roomdbapp.app.other

import android.content.Context
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.domain.model.DocumentEntry
import com.harman.roomdbapp.domain.model.GravityRecord
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MemoryUtil(val context: Context) {

    suspend fun getCsvList(): List<DocumentEntry> {

        val files = context.filesDir.listFiles()
        return files?.filter { it.canRead() && it.isFile && it.name.endsWith(".csv") }?.map {
            val lines = it.readLines()
            val lastLine = if (lines.isNotEmpty()) lines.last() else ""
            val lastLineList = if (lastLine.isNotBlank()) lastLine.split(",") else emptyList()
            val timeStamp = if (lastLineList.size > 1) lastLineList.last() else ""
            DocumentEntry(it.name, timeStamp)
        } ?: listOf()
    }

    companion object {
        fun GravityRecord.convertToCsv(): String {
            return "${this.record},${this.timestamp}"
        }
    }

    fun addNewCsvFile(filename: String, list: List<GravityRecord>): Boolean {
        return try {
            val file = File(context.filesDir, filename)

            val fw = FileWriter(file.absoluteFile)
            val bw = BufferedWriter(fw)
            list.forEach {
                bw.write(it.convertToCsv())
                bw.newLine()
            }
            bw.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun readCsv(filename: String): List<GravityRecord> {
        val file = File(context.filesDir, filename)
        val resultList = file.readLines().let {
            it.map { line ->
                val array = line.split(",")
                GravityRecord(array[0].toFloat(), array[1].toLong())
            }
        }
        return resultList
    }

    fun saveToSharedPref(newRecord: String) {
        val sharedPref =
            context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(
                context.resources.getString(R.string.saved_record_key),
                newRecord
            )
            apply()
        }
    }

    fun getLastRecord(): String {
        val sharedPref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getString(context.resources.getString(R.string.saved_record_key), "")
            ?: ""
    }
}
