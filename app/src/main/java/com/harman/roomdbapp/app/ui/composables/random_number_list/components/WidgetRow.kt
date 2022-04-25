package com.harman.roomdbapp.app.ui.composables.random_number_list

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.other.compose.Screen
import com.harman.roomdbapp.app.ui.MainActivity
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.WidgetRowItem
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.AutoSizeElementsHandler
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.ScrollingStateHandler
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.calculateWidgetElementScale
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.enableCenterSnapHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun WidgetRow(
    widgetList: List<Widget>,
    autoSizeTextElementHandler: AutoSizeElementsHandler<Float>,
    navController: NavController
) {
    val coroutine = rememberCoroutineScope()
    val localConfiguration = LocalConfiguration.current
    val listState = rememberLazyListState()
    val context = LocalContext.current
    var center by remember { mutableStateOf<Float>(1f) }
    val itemSize = localConfiguration.screenWidthDp / 1.7
    val offset = ((localConfiguration.screenWidthDp - itemSize) / 2).toInt()
    val scrollingStateHandler = remember {
        ScrollingStateHandler(listState) {
            coroutine.launch {
                delay(100)
                enableCenterSnapHelper(listState)
            }
        }
    }
    LazyRow(
        state = listState,
        modifier = Modifier.drawWithContent {
            scrollingStateHandler.startScrollObserving()
            center = this.center.x - offset / 2
            drawContent()
        }
    ) {

        items(
            widgetList,
            key = { it.id }
        ) { widget ->

            var scale by remember {
                mutableStateOf(1f)
            }
            scale = calculateWidgetElementScale(
                listState, widget, widgetList.size, center, offset
            )
            Spacer(modifier = Modifier.width(if (widget.id == 0) offset.dp else 0.dp))
            WidgetRowItem(
                scale = scale,
                drawable = widget.imageResourceId,
                elemSize = itemSize,
                maxLines = widget.text.split("\n").size,
                text = widget.text,
                onItemClickCallback = {
                    when (widget.id) {
                        1 -> navController.navigate(Screen.DocumentWidgetRoute.route)
                        2 -> {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        }
                        else -> Unit
                    }
                },
                autoSizeTextHandler = autoSizeTextElementHandler
            )
            Spacer(modifier = Modifier.width(if (widget.id == widgetList.size - 1) offset.dp else 0.dp))
        }
    }
}
