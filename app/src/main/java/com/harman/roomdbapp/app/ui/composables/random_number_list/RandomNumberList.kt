package com.harman.roomdbapp.app.ui.composables.random_number_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.model.Widget
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.RandomNumbersListItem

// TODO populate list from view model
// and add navigation
@Composable
fun RandomNumberList() {

    Box(
        Modifier.fillMaxSize()
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
                )
            )
            Divider(thickness = 2.dp)
            Spacer(
                modifier = Modifier
                    .height(3.dp)
                    .background(Color.Black)
            )
            Divider(thickness = 2.dp)
            Spacer(
                modifier = Modifier
                    .height(3.dp)
                    .background(Color.Black)
            )

            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    RandomNumbersListItem()
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 20.dp),
        ) {

            FloatingActionButton(
                onClick = { /*TODO navigate to newFragment*/ },
                backgroundColor = colorResource(id = R.color.blue),
                contentColor = Color.White,
                modifier = Modifier.width(60.dp).height(60.dp),

            ) {
                val painter = painterResource(id = R.drawable.ic_plus)
                Icon(
                    modifier = Modifier.padding(15.dp),
                    painter = painter,
                    contentDescription = stringResource(id = R.string.add_new_random_number)
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    RandomNumberList()
}
