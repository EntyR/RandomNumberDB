package com.harman.roomdbapp.app.ui.composables.random_number_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.RandomNumbersListItem
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.compose.getViewModel

// TODO populate list from view model ,
//  add navigation,
//  add on click listeners
@Composable
fun RandomNumberList(
    navController: NavController,
    viewModel: NumberListViewModel = getViewModel()
) {

    val numbers by viewModel.getNumbers().observeAsState(emptyList())

    var isReadyToBeDrawn by remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .fillMaxSize()
            .drawWithContent {

                if (isReadyToBeDrawn)
                    drawContent()
            }
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {
            WidgetRow(
                listOf<Widget>(
                    Widget(
                        stringResource(R.string.record_gravity_fluctuation),
                        R.drawable.grav_widget,
                        id = 0
                    ),
                    Widget(
                        stringResource(R.string.data_storage_widget),
                        R.drawable.ic_data_storage,
                        id = 1
                    ),
                    Widget(
                        stringResource(R.string.switch_to_xml),
                        R.drawable.switch_to_icon,
                        id = 2
                    )
                ),
                onCalculationCompleted = {
                    isReadyToBeDrawn = true
                }
            )

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
            ) {

                items(numbers) { item ->
                    RandomNumbersListItem(item.number.toString()) {
                        val bundle = bundleOf(ARGS_KEY to item.number)
                        // TODO Navigate to description screen
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 82.dp, end = 32.dp),
        ) {

            FloatingActionButton(
                onClick = { /*TODO navigate to newFragment*/ },
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
