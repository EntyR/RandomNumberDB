package com.harman.roomdbapp.app.ui.composables.random_number_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.ui.composables.TransparentGray


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WidgetRowItem(
    drawable: Int,
    elemSize: Double,
    modifier: Modifier = Modifier,
    maxLines: Int,
    text: String,
    scale: Float = 1F,
    fontSizeValue: Float,
    changeTextCallback: (value: Float) -> Unit,
    onItemClickCallback: () -> Unit
) {

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .scale(scale)
                .width(elemSize.dp)
                .height(elemSize.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Black,
                            TransparentGray
                        )
                    ),
                    shape = RoundedCornerShape(10)
                )

        ) {
            Card(
                onClick = {
                    onItemClickCallback()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 5.dp),
                shape = RoundedCornerShape(10),
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = (elemSize / 8).dp,
                        top = (elemSize / 13).dp
                    )
                ) {
                    val painter: Painter = painterResource(id = drawable)
                    Image(
                        modifier = Modifier
                            .width((elemSize / 2.8).dp),
                        painter = painter,
                        contentDescription = ""
                    )
                    AutoSizeText(
                        Modifier.padding(
                            top = ((elemSize / 10) / (maxLines / 2)).dp,
                            bottom = (elemSize / 8).dp
                        ),
                        text = text,
                        minTextSize = 20.sp,
                        maxLines = maxLines,
                        fontSizeValue = fontSizeValue,
                        changeTextCallBack = { changeTextCallback(it) }
                    )
                }
            }
        }
    }
}
