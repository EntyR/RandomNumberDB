package com.harman.roomdbapp.app.ui.composables.random_number_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.AutoSizeElementsHandler
import com.harman.roomdbapp.app.ui.composables.random_number_list.utils.CalculationState

@Composable
fun AutoSizeText(
    modifier: Modifier,
    text: String,
    minTextSize: TextUnit,
    step: TextUnit = 1.sp,
    maxLines: Int,
    textSizeHandler: AutoSizeElementsHandler<Float>,

) {

    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        maxLines = maxLines,
        overflow = TextOverflow.Visible,
        fontSize = textSizeHandler.getOverallElementSizeState().sp,
        onTextLayout = {
            if (
                (it.didOverflowHeight || it.didOverflowWidth) &&
                textSizeHandler.getCalculatedState() == CalculationState.CalculationInProcess
            ) {
                val newFontSize = textSizeHandler.getOverallElementSizeState() - step.value
                if (newFontSize <= minTextSize.value) {
                    textSizeHandler.emitNewValue(newFontSize)
                    textSizeHandler.emitCalculationComplete()
                } else {
                    textSizeHandler.emitNewValue(newFontSize)
                }
            } else textSizeHandler.emitCalculationComplete()
        },
        modifier = modifier
            .drawWithContent { if (textSizeHandler.getCalculatedState() == CalculationState.CalculationComplete) drawContent() }
            .fillMaxSize()
    )
}
