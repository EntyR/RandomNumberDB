package com.harman.roomdbapp.app.ui.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentRandomNumberDescriptionBinding
import com.harman.roomdbapp.app.other.MathUtils
import com.harman.roomdbapp.app.other.NUMBER_VALUE
import com.harman.roomdbapp.app.other.runOnSdk29orUp
import java.io.IOException
import java.util.UUID

class RandomNumberDescription : Fragment() {

    private var numberValue: Int = 0

    private val externalLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        binding.ivShareBtn.callOnClick()
    }

    private lateinit var binding: FragmentRandomNumberDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            numberValue = it.getInt(NUMBER_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomNumberDescriptionBinding.inflate(inflater, container, false)

        val numberIsEven = MathUtils.isNumberEven(numberValue)

        binding.ivShareBtn.setOnClickListener {
            executeWithPerm {
                val bitmap = getBitMapFromView(binding.llNumberContainer)
                bitmap?.let {
                    val uri = getUriFromBitmap(bitmap, requireContext())
                    val sendImageIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, uri)
                        type = "image/png"
                    }
                    startActivity(sendImageIntent)
                }
            }
        }

        binding.tvNumberValue.text = resources.getString(R.string.number, numberValue)
        binding.tvNumberDescription.text =
            if (numberIsEven)
                resources.getString(R.string.number_is_even)
            else resources.getString(R.string.number_not_even)

        binding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param numValue number to be displayed.
         * @return A new instance of fragment RandomNumberDescription.
         */

        @JvmStatic
        fun newInstance(numValue: Int) =
            RandomNumberDescription().apply {
                arguments = Bundle().apply {
                    putInt(NUMBER_VALUE, numValue)
                }
            }
    }

    private fun getBitMapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun getUriFromBitmap(bitmap: Bitmap, context: Context): Uri? {
        val contentUri = runOnSdk29orUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val value = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${UUID.randomUUID()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }
        val uri = context.contentResolver.insert(contentUri, value) ?: return null

        context.contentResolver.openOutputStream(uri).use {
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, it))
                throw IOException("Couldn't share file")
        }
        return uri
    }

    private fun executeWithPerm(code: () -> Unit) {
        val isPermissionGranted =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted) return code()
        else externalLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
