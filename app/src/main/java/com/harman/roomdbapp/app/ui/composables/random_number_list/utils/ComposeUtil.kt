package com.harman.roomdbapp.app.ui.composables.random_number_list.utils

import androidx.compose.foundation.lazy.LazyListState
import com.harman.roomdbapp.app.model.Widget
import kotlin.math.abs
import kotlin.math.min

/**
 * Find element closest to the center and start animation to center this element
 *
 * Use extraOffset to set additional offset for specific element
 * @param listState ListState of current Row Element
 */
suspend fun enableCenterSnapHelper(listState: LazyListState) {

    val visibleItems = listState.layoutInfo.visibleItemsInfo
    val screenWidth = listState.layoutInfo.viewportEndOffset

    val index = visibleItems.minByOrNull {
        val offsetCenter = screenWidth / 2
        val offset =
            if (it.offset > offsetCenter) it.offset + it.size else it.offset - it.size
        screenWidth / 2 + abs(offset)
    }
    index?.let {
        listState.animateScrollToItem(it.index, -((screenWidth - it.size) / 2))
    }
}

/**
 * Calculate scale of each visible item in the Row Composable depending on there proximity
 * to the center
 *
 * @param listState ListState of current Row Element
 * @param widget current widget to be displayed
 * @param elementSize general amount of elements
 * @param center horizontal center of current Composable
 * @param offset first and last element offset
 */
fun calculateWidgetElementScale(
    listState: LazyListState,
    widget: Widget,
    elementSize: Int,
    center: Float,
    offset: Int = 0
): Float {
    listState.layoutInfo.visibleItemsInfo.find { item ->
        item.key == widget.id
    }?.let {
        val itemOffset: Int = when (it.key) {
            0 -> {
                it.offset + (it.size - offset) / 2
            }
            elementSize - 1 -> {
                it.offset + (it.size - offset) / 2
            }
            else -> {
                it.offset + it.size / 2
            }
        }
        val distance = min(center * 0.8f, abs(center - itemOffset))
        return 1f - 0.3f * (distance / center)
    } ?: return 0.1f
}
