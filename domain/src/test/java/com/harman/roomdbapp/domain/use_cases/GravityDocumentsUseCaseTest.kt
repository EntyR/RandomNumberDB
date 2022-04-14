package com.harman.roomdbapp.domain.use_cases

import com.google.common.truth.Truth
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.repository.IDocumentsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GravityDocumentsUseCaseTest {

    lateinit var repository: IDocumentsRepository

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun `Verify gravity document are adding to repository`() {

        val fileName = "1234.csv"
        val initial = listOf(GravityRecord(1f, 1234))
        val list: MutableList<GravityRecord> = mutableListOf()

        coEvery { repository.addNewCsvFile(fileName, initial) } answers {
            list.addAll(initial)
        }
        val useCase = GravityDocumentsUseCase(repository)
        useCase.addNewGravityDocument(initial)

        Truth.assertThat(list.containsAll(initial)).isTrue()
    }

    @Test
    fun `Verify documents names are returned from repository`() {

        val numbers = listOf(
            "1234.csv",
            "159753.csv"
        )
        every { repository.getCsvList() } returns numbers
        val useCase = GravityDocumentsUseCase(repository)
        Truth.assertThat(useCase.getGravityDocumentsName()).isEqualTo(numbers)
    }

    @Test
    fun `Verify last record is saved`() {

        val init = 16L
        var result = 0L

        coEvery { repository.saveLastRecord(init) } answers {
            result = init
        }
        val useCase = GravityDocumentsUseCase(repository)
        useCase.setLastBackupRecordTimestamp(init)
        Truth.assertThat(result).isEqualTo(result)
    }
    @Test
    fun `Verify last record is received`() {

        val init = 16L
        coEvery { repository.getLastRecord() } returns init
        val useCase = GravityDocumentsUseCase(repository)
        Truth.assertThat(useCase.getLastBackupRecordTimestamp()).isEqualTo(init)
    }

    @Test
    fun `Verify documents entry's are returned from repository`() {
        val fileName = "1234.csv"
        val records = listOf(GravityRecord(1f, 1234))

        every { repository.getGravityRecordsFromDocument(fileName) } returns records
        val useCase = GravityDocumentsUseCase(repository)
        Truth.assertThat(useCase.getAllGravityRecords(fileName)).isEqualTo(records)
    }

    @Test
    fun `Verify gravity document are deleted`() {

        val fileName = "1234.csv"

        val useCase = GravityDocumentsUseCase(repository)
        useCase.deleteGravityDocument(fileName)

        verify { repository.deleteCsvFile(fileName) }
    }
}
