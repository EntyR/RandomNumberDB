package com.harman.roomdbapp.app.other.worker

import android.content.Context
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerParameters
import com.google.common.truth.Truth
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.use_cases.GravityDocumentsUseCase
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DocumentCoroutineWorkerTest {
    private lateinit var context: Context
    private lateinit var params: WorkerParameters
    private lateinit var fluctuationUseCase: GravityFluctuationUseCase
    private lateinit var documentUseCase: GravityDocumentsUseCase

    @BeforeEach
    fun setUp() {
        context = mockk(relaxed = true)
        params = mockk(relaxed = true)
        fluctuationUseCase = mockk(relaxed = true)
        documentUseCase = mockk(relaxed = true)
    }

    @Test
    fun `verify worker will not add new value when timestamp is the same`() {

        val lastTimeStamp = 16L

        val fluctuationRecords = flowOf(
            listOf(
                GravityRecord(2f, 15L),
                GravityRecord(3f, 16L)
            )
        )
        every { fluctuationUseCase.getFluctuationRecords() } returns fluctuationRecords
        every { documentUseCase.getLastBackupRecordTimestamp() } returns lastTimeStamp

        runBlocking {
            val worker =
                DocumentCoroutineWorker(context, params, fluctuationUseCase, documentUseCase)
            val result = worker.doWork()
            Truth.assertThat(result)
                .isEqualTo(Result.success())
            verify(exactly = 0) { documentUseCase.setLastBackupRecordTimestamp(any()) }
            verify(exactly = 0) { documentUseCase.addNewGravityDocument(any()) }
        }
    }

    @Test
    fun `verify worker addNew value`() {

        val lastTimeStamp = 15L

        val initial = GravityRecord(2f, 15L)
        val expected = GravityRecord(3f, 16L)
        val fluctuationRecords = flowOf(
            listOf(initial, expected)
        )

        every { fluctuationUseCase.getFluctuationRecords() } returns fluctuationRecords
        every { documentUseCase.getLastBackupRecordTimestamp() } returns lastTimeStamp

        runBlocking {
            val worker =
                DocumentCoroutineWorker(context, params, fluctuationUseCase, documentUseCase)
            val result = worker.doWork()
            Truth.assertThat(result)
                .isEqualTo(Result.success())
            verify(exactly = 1) { documentUseCase.setLastBackupRecordTimestamp(expected.timestamp) }
            verify(exactly = 1) { documentUseCase.addNewGravityDocument(listOf(expected)) }
        }
    }
}
