package com.harman.roomdbapp.app.ui.composables.random_number_list.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable

/**
 * Observe for scroll events and fire onScrollStoppedCallback when scroll is stopped
 *
 * @param listState ListState of current Row Element
 */

class ScrollingStateHandler(
    private val listState: LazyListState,
    private val onScrollStoppedCallback: () -> Unit
) {
    private var isScrollEventEmitted = false

    fun startScrollObserving() {
        if (listState.isScrollInProgress && !isScrollEventEmitted) {
            isScrollEventEmitted = true
        }
        if (!listState.isScrollInProgress && isScrollEventEmitted) {
            onScrollStoppedCallback()
            isScrollEventEmitted = false
        }
    }
}
