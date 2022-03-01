package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class NumberListViewModel(
    val numberUseCase: RandomNumberUseCase,
    val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getNumbers() = numberUseCase.getNumbers().asLiveData()

    fun addNumbers(number: RandomNumber) {
        viewModelScope.launch(dispatcher) {
            numberUseCase.addNumber(number)
        }
    }

}