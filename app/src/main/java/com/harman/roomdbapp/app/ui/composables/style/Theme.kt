package com.harman.roomdbapp.app.ui.composables.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MainTheme(content: @Composable() () -> Unit) {

    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}