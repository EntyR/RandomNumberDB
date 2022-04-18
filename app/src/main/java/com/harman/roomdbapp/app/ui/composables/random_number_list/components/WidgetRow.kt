package com.harman.roomdbapp.app.ui.composables.random_number_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.WidgetListItem
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.enableSnapHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min

@SuppressLint("UnrememberedMutableState")
@Composable
fun WidgetRow(widgetList: List<Widget>) {
    val isFlingStarted = mutableStateOf(false)

    val coroutine = rememberCoroutineScope()

    val localConfiguration = LocalConfiguration.current
    val listState = rememberLazyListState()

    var center by remember { mutableStateOf<Float>(1f) }

    var fontSize by remember {
        mutableStateOf(50f)
    }

    LazyRow(
        state = listState,
        modifier = Modifier.drawWithContent {

            if (listState.isScrollInProgress && !isFlingStarted.value) {
                isFlingStarted.value = true
            }

            if (!listState.isScrollInProgress && isFlingStarted.value) {

                val paddingOffset = (listState.layoutInfo.viewportEndOffset / 4.7).toInt()

                coroutine.launch {
                    delay(100)
                    enableSnapHelper(listState) {
                        if (it == 0 || it == widgetList.size - 1)
                            paddingOffset
                        else 0
                    }
                }
                isFlingStarted.value = false
            }

            center = this.center.x
            drawContent()
        }
    ) {

        items(widgetList, key = {
            it.id
        }) { widget ->

            var startPadding = 0
            var endPadding = 0

            var scale by remember { mutableStateOf<Float>(1f) }

            // TODO calculate padding another way, because it differ on another devices
            listState.layoutInfo.visibleItemsInfo.forEach { item ->

                if (item.key == widget.id) {
                    val itemOffset: Int
                    when (item.key) {
                        0 -> {
                            startPadding = (listState.layoutInfo.viewportEndOffset / 12)
                            itemOffset = item.offset + (item.size - startPadding) / 2
                        }
                        widgetList.size - 1 -> {
                            endPadding = (listState.layoutInfo.viewportEndOffset / 12)
                            itemOffset = item.offset + (item.size - endPadding) / 2
                        }
                        else -> {
                            itemOffset = item.offset + item.size / 2
                        }
                    }
                    val distance = min(center * 0.9f, abs(center - itemOffset))
                    scale = 1f - 0.3f * (distance / center * 0.9f)
                }
            }
            Spacer(modifier = Modifier.width(startPadding.dp))
            val maxLines = widget.text.split("\n").size
            WidgetListItem(
                scale = scale,
                drawable = widget.imageResourceId,
                screenWidth = localConfiguration.screenWidthDp,
                maxLines = maxLines,
                text = widget.text,
                fontSizeValue = fontSize,
                changeTextCallback = {
                    fontSize = it
                }
            )
            Spacer(modifier = Modifier.width(endPadding.dp))
        }
    }
}
