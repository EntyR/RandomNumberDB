package com.harman.roomdbapp.app.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.other.SENSOR_CHANNEL_ID
import com.harman.roomdbapp.app.ui.MainActivity
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SensorService : LifecycleService() {

    private val useCase: GravityFluctuationUseCase by inject()

    private val dispatcher: CoroutineDispatcher by inject()

    private val _liveData = MutableLiveData<List<Float>>()
    private val liveData: LiveData<List<Float>> = _liveData

    override fun onCreate() {
        super.onCreate()



        val onPressIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 1, onPressIntent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = getString(R.string.sensor_notification_channel_name)
            val importance = IMPORTANCE_DEFAULT
            val channel = NotificationChannel(SENSOR_CHANNEL_ID, name, importance)
            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, SENSOR_CHANNEL_ID)
            .setContentTitle(this.getString(R.string.sensor_notification_tittle))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(getString(R.string.sensor_notification_description))
            .setContentIntent(pendingIntent)
        startForeground(1, notification.build())

        lifecycleScope.launchWhenCreated {
            useCase.deletePreviousValue()
        }

        startGravityCensorObserving()
        liveData.observe(this, { list ->

            if (list.size >= 10) {
                stopSelf()
            } else {
                val previousList = liveData.value
                val newValue = previousList?.singleOrNull {
                    !list.contains(it)
                }
                newValue?.let {
                    lifecycleScope.launchWhenCreated {
                        useCase.addNewItem(it)
                    }
                }
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    private fun startGravityCensorObserving() = lifecycleScope.launch(dispatcher) {
        useCase.getFluctuationsRecord()
            .collect { value ->

                _liveData.value = liveData.value?.toMutableList()?.apply {
                    add(value)
                }
            }
    }
}
