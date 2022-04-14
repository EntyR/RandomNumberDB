package com.harman.roomdbapp.app.other.compose

sealed class Screen(val route: String) {
    object RandomNumberList : Screen("random_number_list_screen")

}
