package com.harman.roomdbapp.app.other

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class SettingContract() : ActivityResultContract<String, Boolean>() {
    override fun createIntent(context: Context, input: String): Intent {
        val uri: Uri = Uri.fromParts("package", context.packageName, null)

        return Intent(input).apply {
            data = uri
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {

        return when (resultCode) {
            Activity.RESULT_OK -> {
                true
            }
            else -> false
        }
    }
}
