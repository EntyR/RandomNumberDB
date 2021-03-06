package com.harman.roomdbapp.app.ui.composables.document_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.ui.composables.style.RobocoFontFamily

@Composable
fun ModalSheetItem(
    modifier: Modifier,
    value: String,
    date: String,
    textSize: TextUnit = 16.sp
) {

    Box(
        modifier = Modifier
            .padding(start = 14.dp, end = 14.dp, top = 5.dp, bottom = 5.dp)
            .fillMaxSize()
    ) {
        Text(
            fontFamily = RobocoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = textSize,
            text = value,
            modifier = modifier.align(Alignment.CenterStart)
        )
        Text(
            fontFamily = RobocoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = textSize,
            text = date,
            modifier = modifier.align(Alignment.CenterEnd)
        )
    }
}
