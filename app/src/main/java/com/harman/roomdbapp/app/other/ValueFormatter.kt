package com.harman.roomdbapp.app.other

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter

class GravityValueFormatter() : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return ""
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return ""
    }
}
