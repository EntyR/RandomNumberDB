package com.harman.roomdbapp.app.ui.composables.random_number_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.RandomNumbersListItem
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.AutoSizeElementsHandler
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.CalculationState
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun RandomNumberList(
    navController: NavController,
    viewModel: NumberListViewModel = getViewModel()
) {
    val widgetList = getWidgetList()
    val autoSizeTextElementHandler = AutoSizeElementsHandler(50f, widgetList.size)
    val numbers by viewModel.getNumbers().observeAsState(emptyList())

    Box(
        Modifier
            .fillMaxSize()
            .drawWithContent {
                // Will draw element only if size of children calculated
                if (autoSizeTextElementHandler.getCalculatedState()
                    == CalculationState.CalculationComplete
                )
                    drawContent()
            }
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            WidgetRow(
                widgetList,
                autoSizeTextElementHandler = autoSizeTextElementHandler,
            )
            Spacer(modifier = Modifier.height(25.dp))

            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
            ) {

                items(numbers) { item ->
                    RandomNumbersListItem(item.number.toString())
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 82.dp, end = 32.dp),
        ) {
            FloatingActionButton(
                onClick = {},
                backgroundColor = colorResource(id = R.color.blue),
                contentColor = Color.White,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp),
            ) {
                val painter = painterResource(id = R.drawable.ic_plus)
                Icon(
                    modifier = Modifier.padding(20.dp),
                    painter = painter,
                    contentDescription = stringResource(id = R.string.add_new_random_number)
                )
            }
        }
    }
}
