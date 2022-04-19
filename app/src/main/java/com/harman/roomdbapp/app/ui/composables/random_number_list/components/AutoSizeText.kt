package com.harman.roomdbapp.app.ui.composables.random_number_list.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.R

@Composable
fun AutoSizeText(
    modifier: Modifier,
    text: String,
    minTextSize: TextUnit,
    step: TextUnit = 1.sp,
    maxLines: Int,
    fontSizeValue: Float,
    changeTextCallBack: (value: Float) -> Unit
) {

    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        maxLines = maxLines,
        overflow = TextOverflow.Visible,
        fontSize = fontSizeValue.sp,
        onTextLayout = {
            if ((it.didOverflowHeight || it.didOverflowWidth) && !readyToDraw) {

                Log.e("TAG", "AutoSizeText: ${it.lineCount}")
                val newFontSize = fontSizeValue - step.value
                if (newFontSize <= minTextSize.value) {
                    readyToDraw = true
                    changeTextCallBack(newFontSize)
                } else {
                    changeTextCallBack(newFontSize)
                }
            } else readyToDraw = true
        },
        modifier = modifier
            .drawWithContent { if (readyToDraw) drawContent() }
            .fillMaxSize()
    )
}
