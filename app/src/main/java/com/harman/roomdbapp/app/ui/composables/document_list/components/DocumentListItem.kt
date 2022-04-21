package com.harman.roomdbapp.app.ui.composables.document_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.composables.TransparentGray

@Composable
fun DocumentListItem(
    modifier: Modifier,
    text: String,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) colorResource(id = R.color.blue) else Color.White

    Box(modifier = modifier.padding(start = 10.dp, end = 10.dp)) {
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
                    .padding(bottom = 3.dp),
                shape = RoundedCornerShape(50),
                onClick = {}
            ) {
                MarqueeText(
                    modifier = Modifier.padding(top = 7.dp, bottom = 7.dp, start = 18.dp),
                    text = text,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            }
        }
    }

}
