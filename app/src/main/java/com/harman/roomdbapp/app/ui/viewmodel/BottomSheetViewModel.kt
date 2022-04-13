package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.use_cases.GravityDocumentsUseCase

class BottomSheetViewModel(
    private val documentsUseCase: GravityDocumentsUseCase
) : ViewModel() {
    fun getGravityRecordsFromDocuments(fileName: String): List<GravityRecord> {
        return documentsUseCase.getAllGravityRecords(fileName)
    }
}
