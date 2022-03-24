package com.harman.roomdbapp.app.adapters.layout_managers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        val scroll = super.scrollHorizontallyBy(dx, recycler, state)

        val center = width / 2
        val startScaleDistance = center * 1.1f

        //TODO add min distance value

        for (i in 0 until childCount) {
            val child = getChildAt(i) ?: continue
            val childCenter = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2
            val distance = center - childCenter
            val scale: Float = 1f + 1f / distance
            if (distance <= startScaleDistance)
                child.scaleX = scale
        }
        return scroll
    }


}

