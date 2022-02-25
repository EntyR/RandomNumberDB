package com.harman.roomdbapp.domain.use_cases

import com.harman.roomdbapp.domain.model.RandomNumber
import com.harman.roomdbapp.domain.repository.RandomNumberRepository

class AddNewNumberUseCase(private val repository: RandomNumberRepository) {

    suspend operator fun invoke(number: RandomNumber){
        repository.addNumber(number)
    }

}
