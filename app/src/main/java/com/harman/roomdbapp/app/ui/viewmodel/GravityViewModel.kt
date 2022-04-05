package com.harman.roomdbapp.app.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.github.mikephil.charting.data.Entry
import com.harman.roomdbapp.app.other.RecordingState
import com.harman.roomdbapp.app.other.getMinutesInMillis
import com.harman.roomdbapp.app.services.SensorService
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase

class GravityViewModel(
    private val useCase: GravityFluctuationUseCase,
    private val appContext: Application
) : ViewModel() {

    private val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> = _recordingState

    var isNewSensorRecord = false

    fun getSensorData() = useCase.getFluctuationRecords().asLiveData().map { gravRecords ->
        val list = gravRecords.sortedBy { it.timestamp }
        list.filterIndexed { index, _ ->
            index >= list.size - 10
        }.map { record ->
            val converted = getMinutesInMillis(record.timestamp).toFloat()

            Entry(converted, record.record)
        }
    }

    fun getRecordingState() {
        val isActive = SensorService.isMyServiceRunning
        if (isActive) {
            _recordingState.value = RecordingState.Started
            isNewSensorRecord = true
        } else _recordingState.value = RecordingState.Stopped
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
