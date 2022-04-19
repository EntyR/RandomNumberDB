package com.harman.roomdbapp.app.ui.composables.random_number_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.WidgetRowItem
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
    val itemSize = localConfiguration.screenWidthDp / 1.7
    val offset = ((localConfiguration.screenWidthDp - itemSize) / 2).toInt()

    LazyRow(
        state = listState,
        modifier = Modifier.drawWithContent {

            if (listState.isScrollInProgress && !isFlingStarted.value) {
                isFlingStarted.value = true
            }

            if (!listState.isScrollInProgress && isFlingStarted.value) {

                coroutine.launch {
                    delay(100)
                    enableSnapHelper(listState) {
                        if (it == 0 || it == widgetList.size - 1)
                            offset
                        else 0
                    }
                }
                isFlingStarted.value = false
            }

            center = this.center.x - offset / 2
            drawContent()
        }
    ) {

        items(widgetList, key = {
            it.id
        }) { widget ->

            var scale by remember { mutableStateOf<Float>(1f) }

            listState.layoutInfo.visibleItemsInfo.forEach { item ->

                if (item.key == widget.id) {
                    val itemOffset: Int = when (item.key) {
                        0 -> {
                            item.offset + (item.size - offset) / 2
                        }
                        widgetList.size - 1 -> {
                            item.offset + (item.size - offset) / 2
                        }
                        else -> {
                            item.offset + item.size / 2
                        }
                    }
                    val distance = min(center * 0.8f, abs(center - itemOffset))
                    scale = 1f - 0.3f * (distance / center)
                }
            }
            Spacer(modifier = Modifier.width(if (widget.id == 0) offset.dp else 0.dp))
            val maxLines = widget.text.split("\n").size
            WidgetRowItem(
                scale = scale,
                drawable = widget.imageResourceId,
                elemSize = itemSize,
                maxLines = maxLines,
                text = widget.text,
                fontSizeValue = fontSize
            ) {
                fontSize = it
            }
            Spacer(modifier = Modifier.width(if (widget.id == widgetList.size - 1) offset.dp else 0.dp))
        }
    }
}
