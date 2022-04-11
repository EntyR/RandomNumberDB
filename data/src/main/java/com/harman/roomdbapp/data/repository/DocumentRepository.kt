package com.harman.roomdbapp.data.repository

import android.content.Context
import com.harman.roomdbapp.data.APP_PREFERENCES
import com.harman.roomdbapp.data.R
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.repository.IDocumentsRepository
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class DocumentRepository(private val context: Context) : IDocumentsRepository {

    private val sharedPref =
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun getCsvList(): List<String> {
        val files = context.filesDir.listFiles()
        return files?.filter { it.canRead() && it.isFile && it.name.endsWith(".csv") }?.map {
            it.name
        } ?: listOf()
    }

    companion object {
        fun GravityRecord.convertToCsv(): String {
            return "${this.record},${this.timestamp}"
        }
        fun String.convertCsvStringToGravityRecord(): GravityRecord {
            this.split(",").let {
                return GravityRecord(it[0].toFloat(), it[1].toLong())
            }

        }
    }

    override fun addNewCsvFile(filename: String, list: List<GravityRecord>): Boolean {
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

    override fun getGravityRecordsFromDocument(filename: String): List<GravityRecord> {
        val file = File(context.filesDir, filename)
        val resultList = file.readLines().let {
            it.map { line ->
                val array = line.split(",")
                GravityRecord(array[0].toFloat(), array[1].toLong())
            }
        }
        return resultList
    }

    override fun saveLastRecord(newRecord: String) {
        with(sharedPref.edit()) {
            putString(
                context.resources.getString(R.string.saved_record_key),
                newRecord
            )
            apply()
        }
    }

    override fun getLastRecord(): String {
        return sharedPref.getString(context.resources.getString(R.string.saved_record_key), "")
            ?: ""
    }
}
