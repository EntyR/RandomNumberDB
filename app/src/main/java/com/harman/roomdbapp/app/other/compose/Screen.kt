package com.harman.roomdbapp.app.other.compose

sealed class Screen(val route: String) {
    object RandomNumberList : Screen("random_number_list_screen")
    object AnnNewRandomNumber : Screen("random_number_add_screen")
    object RandomNumberDescription : Screen("random_number_description_screen")
}
