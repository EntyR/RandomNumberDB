package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harman.roomdbapp.app.other.AddNumberSate
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AddNumberViewModel(
    private val numberUseCase: RandomNumberUseCase,
    private val dispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _isTextAdded = MutableLiveData<AddNumberSate>(AddNumberSate.AddRandom)
    val isTextAdded: LiveData<AddNumberSate> = _isTextAdded

    fun switchState() {
        if (_isTextAdded.value == AddNumberSate.AddCustom)
            _isTextAdded.value = AddNumberSate.AddRandom
        else _isTextAdded.value = AddNumberSate.AddCustom
    }

    fun addNumber(number: RandomNumber) = viewModelScope.launch(dispatchers) {
        numberUseCase.addNumber(number)
        _isTextAdded.postValue(AddNumberSate.Done)
    }
}
