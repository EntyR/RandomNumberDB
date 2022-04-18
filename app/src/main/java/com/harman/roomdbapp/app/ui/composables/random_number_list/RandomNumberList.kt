package com.harman.roomdbapp.app.ui.composables.random_number_list

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.WidgetListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min


var isFlingStarted = mutableStateOf(false)

@Composable
fun RandomNumberList(
//    viewModel: NumberListViewModel = getViewModel()
) {

    val coroutine = rememberCoroutineScope()

    val widgetList = listOf<Widget>(
        Widget(stringResource(R.string.record_gravity_fluctuation), R.drawable.grav_widget),
        Widget(stringResource(R.string.data_storage_widget), R.drawable.ic_data_storage),
        Widget(stringResource(R.string.data_storage_widget), R.drawable.ic_launcher_foreground)
    )
    val localConfiguration = LocalConfiguration.current
    val listState = rememberLazyListState()

    var center by remember { mutableStateOf<Float>(1f) }


    var fontSize by remember {
        mutableStateOf(50f)
    }



    LazyRow(state = listState,
        modifier = Modifier.drawWithContent {

            if (listState.isScrollInProgress && !isFlingStarted.value) {
                isFlingStarted.value = true
                Log.e("TAG!", "RandomNumberList: ${listState.layoutInfo.visibleItemsInfo}")
            }
            var paddingOffset = 0

            if (!listState.isScrollInProgress && isFlingStarted.value) {
                Log.e("TAG", "RandomNumberList: fling is stopped")


                coroutine.launch {
                    delay(100)
                    val visibleItems = listState.layoutInfo.visibleItemsInfo

                    val index = visibleItems.minByOrNull {
                        val offsetCenter = listState.layoutInfo.viewportEndOffset / 2
                        paddingOffset = (listState.layoutInfo.viewportEndOffset / 4.7).toInt()
                        val padding =
                            if (it.index == 0 || it.index == widgetList.size - 1)
                                paddingOffset
                            else 0
                        val offset =
                            if (it.offset > offsetCenter) it.offset + it.size - padding else it.offset - it.size
                        listState.layoutInfo.viewportEndOffset / 2 + abs(offset)
                    }?.index
                    index?.let {
                        val offset =
                            when (it) {
                                0 -> 0
                                widgetList.size - 1 -> paddingOffset
                                else -> paddingOffset
                            }
                        listState.animateScrollToItem(
                            it,
                            -(offset).toInt()
                        )
                    }

                }
                isFlingStarted.value = false

            }

            center = this.center.x
            drawContent()
        }
    ) {

        items(widgetList, key = {
            it.imageResourceId
        }) { widget ->

            var startPadding = 0
            var endPadding = 0

            var scale by remember { mutableStateOf<Float>(1f) }

            listState.layoutInfo.visibleItemsInfo.forEach { item ->


                if (item.key == widget.imageResourceId) {
                    var itemOffset = 0
                    when (item.key) {
                        R.drawable.grav_widget -> {
                            startPadding = (listState.layoutInfo.viewportEndOffset / 9).toInt()
                            itemOffset = item.offset + (item.size - startPadding) / 2
                        }
                        R.drawable.ic_launcher_foreground -> {
                            endPadding = (listState.layoutInfo.viewportEndOffset / 9).toInt()
                            itemOffset = item.offset + (item.size - endPadding) / 2
                        }
                        R.drawable.ic_data_storage -> {
                            itemOffset = item.offset + item.size / 2
                        }
                    }
                    val distance = min(center * 0.9f, abs(center - itemOffset))
                    scale = 1f - 0.3f * (distance / center * 0.9f)
                }


            }
            Spacer(modifier = Modifier.width(if (widget.imageResourceId == R.drawable.grav_widget) (listState.layoutInfo.viewportEndOffset / 9).dp else 0.dp))


            val maxLines = widget.text.split("\n").size
            WidgetListItem(
                scale = scale,
                drawable = widget.imageResourceId,
                modifier = Modifier.padding(
                    end = endPadding.dp
                ),
                screenWidth = localConfiguration.screenWidthDp,
                maxLines = maxLines,
                text = widget.text,
                fontSizeValue = fontSize,
                changeTextCallback = {
                    fontSize = it
                }
            )
        }
    }

}


@Preview
@Composable
fun Preview() {
    RandomNumberList()
}