package com.harman.roomdbapp.data.datasouce

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.harman.roomdbapp.domain.datasource.IGravityCensorDataSource
import com.harman.roomdbapp.domain.model.GravityValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GravitySensorDataSource(val context: Context) : IGravityCensorDataSource {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    private val fluctuationsFlow: MutableStateFlow<List<GravityValue>> =
        MutableStateFlow(emptyList())
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        sensorManager.registerListener(
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    val value = event?.values
                    value?.let { vector ->
                        val gravValue = GravityValue(
                            value[0],
                            value[1],
                            value[2]
                        )
                        scope.launch(Dispatchers.Default) {
                            val list = fluctuationsFlow.value.toMutableList()
                            list.add(gravValue)
                            fluctuationsFlow.emit(list)
                        }
                    }
                }

                override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
            },
            sensor,
            500000
        )
    }

    override suspend fun getCensorEventsFlow(): StateFlow<List<GravityValue>> {

        return fluctuationsFlow
    }
}
