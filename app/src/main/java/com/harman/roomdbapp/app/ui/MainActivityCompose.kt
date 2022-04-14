package com.harman.roomdbapp.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.composethemeadapter.MdcTheme
import com.harman.roomdbapp.app.other.compose.Screen
import com.harman.roomdbapp.app.ui.composables.random_number_list.RandomNumberList

class MainActivityCompose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Surface(color = MaterialTheme.colors.background) {
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


}