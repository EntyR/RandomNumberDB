package com.harman.roomdbapp.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harman.roomdbapp.app.other.AddNumberState
import com.harman.roomdbapp.app.other.MathUtils
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AddNumberViewModel(
    private val numberUseCase: RandomNumberUseCase,
    private val dispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _textState = MutableLiveData<AddNumberState>(AddNumberState.AddRandom)
    val textState: LiveData<AddNumberState> = _textState

    fun switchState(text: String) {
        if (text.isEmpty())
            _textState.value = AddNumberState.AddRandom
        else _textState.value = AddNumberState.AddCustom
    }

    fun addNumber(number: RandomNumber) = viewModelScope.launch(dispatchers) {
        numberUseCase.addNumber(number)
        _textState.postValue(AddNumberState.Done)
    }

    fun generateRandom() {
        addNumber(
            RandomNumber(
                MathUtils.generateRandomNumber()
            )
        )
    }
}
