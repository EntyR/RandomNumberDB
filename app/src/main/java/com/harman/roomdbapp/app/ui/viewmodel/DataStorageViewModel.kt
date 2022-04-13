package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.harman.roomdbapp.domain.use_cases.GravityDocumentsUseCase

class DataStorageViewModel(
    private val documentsUseCase: GravityDocumentsUseCase
) : ViewModel() {
    fun getGravityDocumentNameList(): List<String> {
        return documentsUseCase.getGravityDocumentsName()
    }

    fun deleteGravityDocument(fileName: String) {
        documentsUseCase.deleteGravityDocument(fileName)
    }
}
