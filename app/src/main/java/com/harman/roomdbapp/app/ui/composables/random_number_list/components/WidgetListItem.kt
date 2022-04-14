package com.harman.roomdbapp.app.ui.composables.random_number_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.R

@Composable
fun WidgetListItem(
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .width(200.dp)
            .height(200.dp)
    ) {
        Column(

        )
        {
            val painter: Painter = painterResource(id = R.drawable.grav_widget)
            Image(
                modifier = Modifier
                    .height(80.dp),
                painter = painter,
                contentDescription = ""
            )
            AutoSizeText(
                text = "Gravity Fluctuation",
                20.sp, 50.sp, maxLines = 3
            )
        }
    }

}


@Preview
@Composable
fun Preview() {
    WidgetListItem()
}