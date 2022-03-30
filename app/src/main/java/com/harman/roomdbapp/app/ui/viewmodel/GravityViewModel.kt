package com.harman.roomdbapp.app.ui.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harman.roomdbapp.app.other.RecordingState
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase

class GravityViewModel(
    useCase: GravityFluctuationUseCase
): ViewModel() {

    private val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> = _recordingState

    fun startRecording(){
        _recordingState.value = RecordingState.Started
    }

    fun stopRecording(){
        _recordingState.value = RecordingState.Stopped

    }

}