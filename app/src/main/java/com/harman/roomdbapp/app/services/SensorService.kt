package com.harman.roomdbapp.app.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.other.SENSOR_CHANNEL_ID
import com.harman.roomdbapp.app.ui.MainActivity
import com.harman.roomdbapp.domain.model.GravityRecord
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class SensorService : LifecycleService(), KoinComponent {

    private val gravityFluctuationUseCase: GravityFluctuationUseCase by inject()

    private val dispatcher: CoroutineDispatcher by inject()

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
            gravityFluctuationUseCase.deletePreviousValue()
        }

        gravityFluctuationUseCase.getFluctuationsFlow().asLiveData().observe(this) { value ->

            val record = GravityRecord(value, System.currentTimeMillis())
            Log.d("TAG", "Sensor event: $record")
            lifecycleScope.launch(dispatcher) {
                gravityFluctuationUseCase.addNewItem(
                    record
                )
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }
}
