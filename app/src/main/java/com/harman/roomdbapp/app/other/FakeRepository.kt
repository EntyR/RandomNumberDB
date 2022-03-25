package com.harman.roomdbapp.app.other

import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.model.Widget

object FakeRepository {
    val repository = listOf(
        Widget("Widget \nGravity \nFluctuation", R.drawable.grav_widget),
        Widget("Widget \nGravity \nFluctuation", R.drawable.grav_widget),
        Widget(
            "Widget \nGravity \n" +
                "Fluctuation",
            R.drawable.grav_widget
        ),
        Widget(
            "Widget \n" +
                "Gravity \n" +
                "Fluctuation",
            R.drawable.grav_widget
        ),
        Widget(
            "Widget \n" +
                "Gravity \n" +
                "Fluctuation",
            R.drawable.grav_widget
        ),
        Widget(
            "Widget \n" +
                "Gravity \n" +
                "Fluctuation",
            R.drawable.grav_widget
        ),
        Widget(
            "Widget \n" +
                "Gravity \n" +
                "Fluctuation",
            R.drawable.grav_widget
        )
    )
}
