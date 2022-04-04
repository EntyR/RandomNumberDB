package com.harman.roomdbapp.app.other

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter

class PointValueFormatter : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return ""
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return ""
    }
}

class XValueFormatter : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return ""
    }

    override fun getFormattedValue(value: Float): String {

        return TimeUtil.getFormattedTimeFromMillis(value.toLong())
    }
}

class YValueFormatter : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return ""
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        if (value == 0f) {
            return ""
        }
        return "$value"
    }
}
