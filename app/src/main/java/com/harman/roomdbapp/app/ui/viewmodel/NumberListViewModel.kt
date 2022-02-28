package com.harman.roomdbapp.app.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.harman.roomdbapp.app.RandomNumberApplication
import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NumberListViewModel(ctx: Application): AndroidViewModel(ctx), KoinComponent {

    private val repository: IRandomNumberRepository by inject()

    private var randomNumbers: LiveData<List<RandomNumber>>? = null

    fun getNumbers(): LiveData<List<RandomNumber>>? {
        return if (randomNumbers == null) {
            randomNumbers = repository.getNumbers().asLiveData()
            randomNumbers
        } else randomNumbers
    }

    fun addNumbers(number: RandomNumber){
        viewModelScope.launch {
            repository.addNumber(number)
        }
    }

}