package com.harman.roomdbapp.app.ui.composables.random_number_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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

@Composable
fun WidgetListItem(
    drawable: Int,
    screenWidth: Int,
    modifier: Modifier = Modifier,
    maxLines: Int,
    text: String,
    scale: Float = 1F,
    fontSizeValue: Float,
    changeTextCallback: (value: Float) -> Unit
) {

    val elemSize = screenWidth / 1.7

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
                )
                {
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
                        20.sp,
                        50.sp,
                        maxLines = maxLines,
                        fontSizeValue = fontSizeValue,
                        changeTextCallBack = { changeTextCallback(it) }
                    )
                }
            }
        }
    }


}


//@Preview
//@Composable
//fun Preview() {
//    WidgetListItem(
//        R.drawable.grav_widget,
//        500,
//        Modifier,
//        3,
//        "Gravity \nFluctuation \nRecord"
//    )
//}