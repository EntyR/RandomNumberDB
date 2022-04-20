package com.harman.roomdbapp.app.ui.composables.random_number_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.model.Widget

@Composable
fun getWidgetList() = listOf<Widget>(
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
)
