package com.harman.roomdbapp.data.repository

import android.content.Context
import com.google.common.truth.Truth
import com.harman.roomdbapp.data.repository.DocumentRepository.Companion.convertToCsv
import com.harman.roomdbapp.domain.model.GravityRecord
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

internal class DocumentRepositoryTest {
    lateinit var context: Context

    @BeforeEach
    fun setUp() {
        context = mockk(relaxed = true) {
            every { filesDir } returns mockk(relaxed = true)
            every { getSharedPreferences(any(), any()) } returns mockk(relaxed = true)
            every { getSharedPreferences(any(), any()).edit() } returns mockk(relaxed = true)
        }
    }

    @AfterEach
    fun tearDown() {
        unmockkConstructor(File::class)
    }

    @Test
    fun `verify documents name are returned`() {

        val initName = "1234.csv"

        val file = mockk<File>(relaxed = true) {
            every { canRead() } returns true
            every { isFile } returns true
            every { name } returns initName
        }

        every { context.filesDir.listFiles() } returns listOf(file).toTypedArray()

        val repository = DocumentRepository(context)
        Truth.assertThat(repository.getCsvList())
            .contains(initName)
    }

    // mock constructor for File throws stackoverflow, used mockito library instead
    // see https://github.com/mockk/mockk/issues/121
    @Test
    fun `verify document are added`() {

        val initName = "1234.csv"
        val list = mutableListOf<String>()
        val initValue = GravityRecord(1f, 1L)
        val records = listOf(initValue)

        Mockito.mockConstruction(File::class.java) { file, _ ->
            `when`(file.absoluteFile).thenReturn(Mockito.mock(File::class.java))
        }

        Mockito.mockConstruction(FileWriter::class.java)

        Mockito.mockConstruction(BufferedWriter::class.java) { writer, _ ->
            `when`(writer.write(initValue.convertToCsv())).thenAnswer { list.add(initValue.convertToCsv()) }
            `when`(writer.newLine()).thenAnswer { }
        }

        val repository = DocumentRepository(context)
        repository.addNewCsvFile(initName, records)
        Truth.assertThat(list).containsExactly(initValue.convertToCsv())
    }

    // mock constructor for File throws stackoverflow, used mockito library instead
    // see https://github.com/mockk/mockk/issues/121
    @Test
    fun `verify document deleted`() {

        var result = false
        Mockito.mockConstruction(File::class.java) { file, _ ->
            `when`(file.delete()).then { true.also { result = it } }
        }
        val initName = "1234.csv"
        val repository = DocumentRepository(context)
        repository.deleteCsvFile(initName)
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `verify last record are saved`() {

        val init = 1234L
        every {
            context.getSharedPreferences(any(), any()).edit().putLong(any(), init)
        } returns mockk(relaxed = true)
        every {
            context.getSharedPreferences(any(), any()).edit().apply()
        } returns mockk(relaxed = true)
        val repository = DocumentRepository(context)
        repository.saveLastRecord(init)
        io.mockk.verify { context.getSharedPreferences(any(), any()).edit().putLong(any(), init) }
    }

    @Test
    fun `verify last is received`() {
        val init = 1234L
        every { context.getSharedPreferences(any(), any()).getLong(any(), any()) } returns init
        val repository = DocumentRepository(context)
        Truth.assertThat(repository.getLastRecord()).isEqualTo(init)
    }

    @Test
    fun `verify convert is right`() {
        val init = GravityRecord(1f, 1)
        val result = "1.0,1"
        Truth.assertThat(init.convertToCsv()).isEqualTo(result)
    }
}
