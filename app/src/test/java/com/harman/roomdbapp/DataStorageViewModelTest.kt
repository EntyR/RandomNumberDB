package com.harman.roomdbapp

import com.google.common.truth.Truth
import com.harman.roomdbapp.app.ui.viewmodel.DataStorageViewModel
import com.harman.roomdbapp.domain.use_cases.GravityDocumentsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DataStorageViewModelTest {

    private lateinit var useCase: GravityDocumentsUseCase

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
    }

    @Test
    fun `Verify gravity document list is received`() {

        val initial = listOf("initValue")

        coEvery { useCase.getGravityDocumentsName() } returns initial
        val viewModel = DataStorageViewModel(useCase)
        val received = viewModel.getGravityDocumentNameList()

        Truth.assertThat(received).isEqualTo(initial)
    }

    @Test
    fun `Verify gravity document is deleted`() {
        val fileName = "123456.csv"
        val initial = mutableListOf(fileName)

        coEvery { useCase.deleteGravityDocument(fileName) } answers {
            initial.remove(fileName)
        }
        val viewModel = DataStorageViewModel(useCase)
        viewModel.deleteGravityDocument(fileName)

        Truth.assertThat(initial).doesNotContain(fileName)
    }
}
