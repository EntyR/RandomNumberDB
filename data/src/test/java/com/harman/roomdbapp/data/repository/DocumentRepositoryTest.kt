package com.harman.roomdbapp.data.repository

import android.content.Context
import com.google.common.truth.Truth
import com.harman.roomdbapp.domain.model.GravityRecord
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

internal class DocumentRepositoryTest {
    lateinit var context: Context

    @BeforeEach
    fun setUp() {
        context = mockk(relaxed = true) {
            every { filesDir } returns mockk(relaxed = true)
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

    //TODO fix, mock constructor throws stackoverflow,
    // see https://github.com/mockk/mockk/issues/121
    @Test
    fun `verify document are added`() {

        val initName = "1234.csv"

        mockkConstructor(File::class) {
            every { anyConstructed<File>().absoluteFile } returns mockk(relaxed = true)
        }

        val records = listOf(GravityRecord(1f, 1L))

        val repository = DocumentRepository(context)
        repository.addNewCsvFile(initName, records)
        Truth.assertThat(true).isTrue()
    }

    @Test
    fun `verify document deleted`() {
        mockkConstructor(File::class)
        every { constructedWith<File>().delete() } returns mockk(relaxed = true)
        val initName = "1234.csv"


        val repository = spyk(DocumentRepository(context))
        repository.deleteCsvFile(initName)
        verify { anyConstructed<File>().delete() }
    }
}
