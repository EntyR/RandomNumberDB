package com.harman.roomdbapp

import com.google.common.truth.Truth
import com.harman.roomdbapp.app.ui.viewmodel.BottomSheetViewModel
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.use_cases.GravityDocumentsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BottomSheetViewModelTest {
    private lateinit var useCase: GravityDocumentsUseCase

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
    }

    @Test
    fun `Verify gravity document entry's are received`() {

        val fileName = "123456.csv"
        val initial = listOf(GravityRecord(1f, 1L))

        coEvery { useCase.getAllGravityRecords(fileName) } returns initial
        val viewModel = BottomSheetViewModel(useCase)
        val received = viewModel.getGravityRecordsFromDocuments(fileName)

        Truth.assertThat(received).isEqualTo(initial)
    }
}
