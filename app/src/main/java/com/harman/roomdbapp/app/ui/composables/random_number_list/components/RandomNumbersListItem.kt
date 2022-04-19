package com.harman.roomdbapp.app.ui.composables.random_number_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.composables.TransparentGray

@Composable
fun RandomNumbersListItem() {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) colorResource(id = R.color.blue) else Color.White

    Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Black,
                            TransparentGray
                        )
                    ),
                    shape = RoundedCornerShape(50)
                )
        ) {
            Button(
                interactionSource = interactionSource,
                colors = buttonColors(backgroundColor = color),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 5.dp),
                shape = RoundedCornerShape(50),
                onClick = {
                }
            ) {

                Text(
                    modifier = Modifier
                        .padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontSize = 18.sp,
                    text = "1234",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
