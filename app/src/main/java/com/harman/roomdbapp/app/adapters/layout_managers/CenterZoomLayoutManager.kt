package com.harman.roomdbapp.app.adapters.layout_managers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min

class CenterZoomLayoutManager(
    context: Context,
    orientation: Int,
    reversLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reversLayout) {

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (orientation == RecyclerView.VERTICAL)
            return 0

        val scroll = super.scrollHorizontallyBy(dx, recycler, state)

        centerMiddle()

        return scroll
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        centerMiddle()
    }

    private fun centerMiddle() {

        val center = width / 2
        val startScaleDistance = center * 0.9f

        for (i in 0 until childCount) {
            val child = getChildAt(i)!!
            val childCenter = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2
            val distance = min(startScaleDistance, abs(center - childCenter).toFloat())
            val scale: Float = 1f - 0.3f * (distance / startScaleDistance)

            child.scaleX = scale
            child.scaleY = scale
        }
    }
}
