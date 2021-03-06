package com.harman.roomdbapp.data.datasouce

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.harman.roomdbapp.domain.datasource.IGravitySensorDataSource
import com.harman.roomdbapp.domain.model.GravityValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

class GravitySensorDataSource(val context: Context) : IGravitySensorDataSource {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    override fun getSensorFlow() = callbackFlow {

        var lastEventTimestamp = 0L
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val value = event?.values
                value?.let { vector ->
                    val gravValue = GravityValue(
                        vector[0],
                        vector[1],
                        vector[2]
                    )
                    if (System.currentTimeMillis() - lastEventTimestamp >= SENSOR_INTERVAL_MILLIS) {
                        lastEventTimestamp = System.currentTimeMillis()
                        trySend(gravValue)
                    }
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
        }

        sensorManager.registerListener(
            listener,
            sensor,
            TimeUnit.MILLISECONDS.toMicros(SENSOR_INTERVAL_MILLIS).toInt()
        )
        awaitClose {
            sensorManager.unregisterListener(listener, sensor)
        }
    }

    companion object {
        private const val SENSOR_INTERVAL_MILLIS = 2000L
    }
}
