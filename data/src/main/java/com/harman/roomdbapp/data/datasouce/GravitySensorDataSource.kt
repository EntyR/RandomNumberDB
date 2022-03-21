package com.harman.roomdbapp.data.datasouce

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.harman.roomdbapp.domain.datasource.IGravityCensorDataSource
import com.harman.roomdbapp.domain.model.GravityValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class GravitySensorDataSource(val context: Context) : IGravityCensorDataSource {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    override suspend fun getCensorFlow() = callbackFlow {

        val list: MutableList<GravityValue> = mutableListOf()

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val value = event?.values
                value?.let { vector ->
                    val gravValue = GravityValue(
                        value[0],
                        value[1],
                        value[2]
                    )
                    list.add(gravValue)
                    trySend(list)
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
        }

        sensorManager.registerListener(
            listener,
            sensor,
            500000
        )
        awaitClose {
            sensorManager.unregisterListener(listener, sensor)
        }
    }
}
