package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase

class NumberListViewModel(
    private val numberUseCase: RandomNumberUseCase
) : ViewModel() {

    fun getNumbers() = numberUseCase.getNumbers().asLiveData()
}