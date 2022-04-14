package com.harman.roomdbapp.app.ui.composables.random_number_list

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.harman.roomdbapp.app.ui.composables.random_number_list.components.RandomNumberListItem
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun RandomNumberList(
    navController: NavHostController,
    viewModel: NumberListViewModel = getViewModel()
) {

    RandomNumberListItem()

}