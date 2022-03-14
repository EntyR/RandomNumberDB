package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AddNumberViewModel(
    private val numberUseCase: RandomNumberUseCase,
    private val dispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _isTextAdded = MutableLiveData<Boolean>(false)
    val isTextAdded: LiveData<Boolean> = _isTextAdded

    fun switchState() {
        _isTextAdded.value = !_isTextAdded.value!!
    }

    fun addNumber(number: RandomNumber) = viewModelScope.launch(dispatchers) {
        numberUseCase.addNumber(number)
    }
}
