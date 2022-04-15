package com.harman.roomdbapp.app.ui.composables.random_number_list

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.RandomNumberListItem
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.WidgetListItem
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.core.definition.indexKey
import kotlin.math.abs
import kotlin.math.min

@Composable
fun RandomNumberList(
//    viewModel: NumberListViewModel = getViewModel()
) {
    val widgetList = listOf<Widget>(
        Widget(stringResource(R.string.record_gravity_fluctuation), R.drawable.grav_widget),
        Widget(stringResource(R.string.data_storage_widget),  R.drawable.ic_data_storage)
    )
    val localConfiguration = LocalConfiguration.current
    val listState = rememberLazyListState()
    var center by remember { mutableStateOf<Float>(1f) }

    LazyRow(state = listState,
        modifier = Modifier.drawWithContent {
            center = this.center.x
            drawContent()
        },
    ){
        items(widgetList, key = {
            it.imageResourceId
        }) { widget ->

            var scale by remember { mutableStateOf<Float>(1f) }

            listState.layoutInfo.visibleItemsInfo.forEach { item ->
                if (item.key == widget.imageResourceId){
                    item.offset
                    val distance = min(center*0.9f, abs(center - item.offset))
                    scale = 1f - 0.3f * (distance / center*0.9f)
                }
            }

            val maxLines = widget.text.split("\n").size
            WidgetListItem(
                scale = scale,
                drawable = widget.imageResourceId,
                screenWidth = localConfiguration.screenWidthDp,
                maxLines = maxLines,
                text = widget.text
            )
        }
    }

}


@Preview
@Composable
fun Preview() {
    RandomNumberList()
}