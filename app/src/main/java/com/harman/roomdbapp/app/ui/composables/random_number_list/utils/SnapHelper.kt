package com.harman.roomdbapp.app.ui.composables.random_number_list.utils

import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.abs

/**
 * Find element closest to the center and start animation to center this element
 *
 * Use extraOffset to set additional offset for specific element
 */
suspend fun enableSnapHelper(listState: LazyListState, extraOffset: () -> Int) {

    val visibleItems = listState.layoutInfo.visibleItemsInfo

    val index = visibleItems.minByOrNull {
        val offsetCenter = listState.layoutInfo.viewportEndOffset / 2
        val padding = extraOffset()
        val offset =
            if (it.offset > offsetCenter) it.offset + it.size - padding else it.offset - it.size
        listState.layoutInfo.viewportEndOffset / 2 + abs(offset)
    }
    index?.let {
        listState.animateScrollToItem(it.index, -((listState.layoutInfo.viewportEndOffset - it.size)/2))
    }
}
