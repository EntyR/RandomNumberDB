package com.harman.roomdbapp.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
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