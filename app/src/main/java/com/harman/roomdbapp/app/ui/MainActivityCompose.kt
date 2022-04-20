package com.harman.roomdbapp.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.other.compose.Screen
import com.harman.roomdbapp.app.ui.composables.random_number_list.RandomNumberList

class MainActivityCompose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = colorResource(id = R.color.pale_yellow)) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.RandomNumberList.route
                ) {
                    composable(
                        route = Screen.RandomNumberList.route
                    ) {
                        RandomNumberList(navController)
                    }
                }
            }
        }
    }
}
