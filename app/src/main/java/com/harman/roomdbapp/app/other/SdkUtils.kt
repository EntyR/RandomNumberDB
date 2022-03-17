package com.harman.roomdbapp.app.other

import android.os.Build

fun <T> runOnSdk29orUp(functionToRun: () -> T): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        functionToRun()
    } else null
}
