package com.harman.roomdbapp.data.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.R.string.censor_notification_channel_name
import com.harman.roomdbapp.app.other.CENSOR_CHANNEL_ID
import com.harman.roomdbapp.app.ui.MainActivity
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CensorService(
    private val repository: IGravityFluctuationsRepository
) : LifecycleService() {

    private val _liveData = MutableLiveData<List<Float>>()
    val liveData: LiveData<List<Float>> = _liveData

    private val binder = CensorServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val onPressIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 1, onPressIntent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = getString(censor_notification_channel_name)
            val importance = IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CENSOR_CHANNEL_ID, name, importance)
            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CENSOR_CHANNEL_ID)
            .setContentTitle(this.getString(R.string.censor_notification_tittle))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(getString(R.string.censor_notification_description))
            .setContentIntent(pendingIntent)
        startForeground(1, notification.build())
        return START_STICKY
    }

    fun startGravityCensorObserving() = lifecycleScope.launch(Dispatchers.Default) {
        repository.getGravityFluctuationsRecord().collect {
            _liveData.value = it
        }
    }

    inner class CensorServiceBinder : Binder() {
        fun getService(): CensorService {
            return this@CensorService
        }
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }
}
