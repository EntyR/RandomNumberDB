package com.harman.roomdbapp.app.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harman.roomdbapp.app.other.RecordingState
import com.harman.roomdbapp.app.services.SensorService
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase

class GravityViewModel(
    private val useCase: GravityFluctuationUseCase,
    private val appContext: Application
) : ViewModel() {

    private val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> = _recordingState

    fun getRecordingState() {
        val isActive = SensorService.isMyServiceRunning
        if (isActive) _recordingState.value = RecordingState.Started
        else _recordingState.value = RecordingState.Stopped
    }

    fun startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            appContext.startForegroundService(Intent(appContext, SensorService::class.java))
        } else appContext.startService(Intent(appContext, SensorService::class.java))
    }

    fun stopRecording() {
        val intent = Intent(appContext, SensorService::class.java)
        appContext.stopService(intent)
    }

    fun switchRecordState() {
        if (recordingState.value == RecordingState.Started) {
            _recordingState.value = RecordingState.Stopped
        } else _recordingState.value = RecordingState.Started
    }
}
